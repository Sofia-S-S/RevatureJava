package com.sverbank.main;

import java.text.ParseException;

import java.util.Date;
import java.util.List;
import java.util.Scanner;


import com.sverbank.dao.CustomerDAO;
import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;
import com.sverbank.model.Transfer;
import com.sverbank.service.CustomerService;
import com.sverbank.service.impl.CustomerServiceImpl;


public class BankMain {

	public static void main(String[] args) throws BusinessException, ParseException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Sverbank App V1.0");
		System.out.println("---------------------------------------");
		int ch = 0;
		CustomerDAO dao = new CustomerDAOImpl();
		CustomerService service = new CustomerServiceImpl();
		do {
			System.out.println("MAIN MENU");
			System.out.println("===========================");
			System.out.println("1)Log in to my account");
			System.out.println("2)New Customer");
			System.out.println("3)Employee log in");
			
			System.out.println("Please enter appropriate choice between 1-3");
			try {
				ch = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
			}

			switch (ch) {
//--1------------------------------------Customer-------------------------------------	
			
			//------------------------------------Customer log in ----------------------------			
			case 1:
				try {
					System.out.println("Customer log in page");
				
					System.out.println("Enter your LOGIN");
					String login = sc.nextLine();	
					System.out.println("Enter your PASSWORD");
					String password = sc.nextLine();	
				
					CustomerLogin customer_login = service.letCustomerLogin(login, password);
					int loggedCustomerId = customer_login.getCustomer_id(); // ID of Logged IN Customer
					Customer customer = service.getCustomerById(customer_login.getCustomer_id());
					List<Account> accountsList = service.getAccountsById(customer.getId());
					
					
					if (customer_login != null && customer !=null) {
						System.out.println("\nHello "+ customer.getFirst_name());
						System.out.println("\nYour accounts:\n");
						for(Account a : accountsList) {
							System.out.println(a.getAccount_number()+"  |  "+a.getStatus()+"  |  $"+a.getBalance());
						}
						//----------------------Customer menu-----------------------------
						int chC = 0;
						
						do {
							System.out.println("\nCustomer MENU");
							System.out.println("===========================");
							System.out.println("1)Withdraw money");
							System.out.println("2)Add funds");
							System.out.println("3)Send money");
							System.out.println("4)Receive money");

							System.out.println("5)EXIT");
							System.out.println("Please enter appropriate choice between 1-5");
							try {
								chC = Integer.parseInt(sc.nextLine());
							} catch (NumberFormatException e) {
							}
							//--1.1--------------- Withdraw money ----------------------------------

							switch (chC) {
							case 1:
								try {
									System.out.println("Enter account number you woul like to access) ");
									long account_number = Long.parseLong(sc.nextLine());
									// Check status of account
									Account account = service.getAccountByNumber(account_number);
									if (account.getCustomer_id()==loggedCustomerId && account.getStatus().equals("active")) {
										System.out.println("How much money would you like to withdraw");
										double transfer = Double.parseDouble(sc.nextLine());
										double balance = account.getBalance();
										double newBalance = balance - transfer;
										// update account balance
										Account acc = service.updateAccountBalance(account_number, newBalance);
										if (acc != null) {
											System.out.println("\nYour new balance is: $ "+newBalance);
										}else {
											System.out.println("\nCould not update balance");}
									}else {
										System.out.println("\nWrong account number or your account is not active");
									};
								} catch (BusinessException e) {
									System.out.println(e.getMessage());
								}
							break;
							
							//--1.2------------------------ Add money ----------------------------------		
							
							case 2:
								try {
									System.out.println("Enter account number you woul like to access) ");
									long account_number = Long.parseLong(sc.nextLine());
									// Check status of account
									Account account = service.getAccountByNumber(account_number);
									if (account.getCustomer_id()==loggedCustomerId && account.getStatus().equals("active")) {
										System.out.println("How much money would you like to withdraw");
										double transfer = Double.parseDouble(sc.nextLine());
										double balance = account.getBalance();
										double newBalance = balance + transfer;
										// update account balance
										Account acc = service.updateAccountBalance(account_number, newBalance);
										if (acc != null) {
											System.out.println("\nYour new balance is: $ "+newBalance);
										}else {
											System.out.println("\nCould not update balance");}
									}else {
										System.out.println("\nYou account is not active");
									};
								} catch (BusinessException e) {
									System.out.println(e.getMessage());
								}
							break;
							
							//--1.3-------------------Transfer money ----------------------------------		
							
							case 3:
								
								System.out.println("Enter account number you would like to send money from) ");
								long  sender_acc_num = Long.parseLong(sc.nextLine());
																
								//Check if account belongs to logged in customer
								Account sender = service.getAccountByNumber(sender_acc_num);	//run to db
								int accountOwnerId = sender.getCustomer_id();
								if (loggedCustomerId==accountOwnerId) {
									System.out.println("It is your account - passed");
									//Check if account has an active status
									if (sender.getStatus().equals("active")) {
										System.out.println("Account is active - passed");
										System.out.println("Enter recipient account number) ");
										long  receiver_acc_num = Long.parseLong(sc.nextLine());
										//Check if sender and recipient are different accounts
										if (sender_acc_num != receiver_acc_num) {
											//Validate recipient account
											Account receiver = service.getAccountByNumber(receiver_acc_num); //run to db
											if (receiver.getStatus().equals("active")) {
												System.out.println("How much money would you like to send");
												double balance = sender.getBalance();
												double amount = Double.parseDouble(sc.nextLine());
												//Check amount of money to send and available balance
												if (amount>0 && amount<sender.getBalance()) {
													//Take money from account
													Date date = new Date();
													
													//Create new Transfer
													Transfer transfer = new Transfer( sender_acc_num, receiver_acc_num, amount, date);
													double newBalance = balance -amount;
													//Subtract from balance (customer) && add to amount (transfer)			
													try {
														dao.sendMoney(transfer,sender_acc_num,newBalance);								//run to db
														System.out.println("Money send successfully. Wait fot recipient to approve transfer");
														
													} catch (BusinessException e) {
														System.out.println(e.getMessage());
													}

												} else 
													{System.out.println("You can not send less then $1 or more then your available funds $"+sender.getBalance());};
											} else 
												{System.out.println("Recipient account does not accept transfers at the moment");};
										} else {
											System.out.println("You can not send money to the same account");};
									} else {
										System.out.println("This account is not in active status");};
								}else {
									System.out.println("This account does not belong to " +customer.getFirst_name());};
								
							break;
							
							//--1.4------------------------Receive money ----------------------------------		
							
							case 4:
								try {
									System.out.println("Enter your account number ");
									long receiver_acc_num = Long.parseLong(sc.nextLine());
									
//									Validate account here
									Account receiver = service.getAccountByNumber(receiver_acc_num);	//run to db
									int enteredAccountId = receiver.getCustomer_id();
									if (loggedCustomerId==enteredAccountId) {
									
									// Check status of account
									List<Transfer> transfersList = dao.getTtransfersByAccNumber(receiver_acc_num);

										if (transfersList != null) {
											System.out.println("\nYour awaiting trunsfers");
											for (Transfer t : transfersList) {
												System.out.println("id: "+t.geTtransfer_id()+" | from: "+t.getSender_acc_num()+ " | $"+t.getAmount()+" | on "+t.getDate());
												
												//---------------------transfer menu------------------
												int chTr=0;
												do {
													System.out.println("Choose 1, 2 or 3");
													System.out.println("===========================");
													System.out.println("1)Accept transfer");
													System.out.println("2)Decline trunsfer");
													System.out.println("3)Exit /To netx transfer");
													try {
														chTr = Integer.parseInt(sc.nextLine());
													} catch (NumberFormatException e) {
													}
													
													int transfer_id = t.geTtransfer_id();
													long sender_acccount = t.getSender_acc_num();
													switch (chTr) {
													//---------------Accept transfer-------------
													case 1:
														try {
															Double balance = dao.getAccountByNumber(receiver_acc_num).getBalance();
															Double amount = dao.getTransferById(transfer_id).getAmount();
															Double newBalance = balance + amount;
															
															dao.approveTransfer(newBalance, receiver_acc_num, transfer_id);
															System.out.println("\nMoney trunsfered to your account sccessfully");
															System.out.println("\nAvailible balance is $"+newBalance);
															
														} catch (BusinessException e) {
															System.out.println(e.getMessage());
															
														} finally {
															chTr=3;
														}
												break;
												
													//---------------Decline transfer------------
												case 2:

														try {
															Double balance = dao.getAccountByNumber(sender_acccount).getBalance();
															Double amount = dao.getTransferById(transfer_id).getAmount();
															Double newBalance = balance + amount;
															dao.approveTransfer(newBalance, sender_acccount, transfer_id);
															System.out.println("\nMoney return to sender sccessfully");
													
															
														} catch (BusinessException e) {
															System.out.println(e.getMessage());
															
														}finally {
															chTr=3;
														}
													break;
													//---------------Exit------------------------
													case 3:
												break;
													
												default:
													System.out.println("Invalid menu option... Kindly Retry................!!!!");
													break;
												}
											} while (chTr != 3);}
											
										}else {
											System.out.println("\nYou do not have awaiting transfers");
											}
									}else {
										System.out.println("\nWrong account number");
										}
											

								} catch (BusinessException e) {
									System.out.println(e.getMessage());
								}
							break;
								
							//--1.5------------------------ Exit ----------------------------------		
								
							case 5:
								System.out.println("Thank uoy for using Sverbank App V1.0. See you soon. ");

								break;
							
						}
							}while (chC != 5);
					}
			} catch (NumberFormatException e) {
					System.out.println("Player Id cannot be special characters or symbols or white spaces it is numeric");
			} catch (BusinessException e) {
					System.out.println(e.getMessage());
			}


			

				break;	
			
//--2---------------------------New Customer Registration-------pending status------------------------------	
		
			case 2:
				System.out.println("__________________________");
				System.out.println("Register new customer page");
				System.out.println("__________________________");
				
				System.out.println("Enter your FIRST NAME");
				String first_name=sc.nextLine();
				System.out.println("Enter your LAST NAME");
				String last_name=sc.nextLine();
				System.out.println("Enter F or M for your gender");
				String gender=sc.nextLine();
				
				
//				System.out.println("Enter your DATE OF BIRTH");
//				sc.useDelimiter(",");
//				String dateString = sc.next();
//			    DateFormat formatter = new SimpleDateFormat("EEEE dd MMM yyyy");
//			    Date dob = formatter.parse(dateString);
//			    System.out.println(dob);
										
		
				System.out.println("Enter your SSN");
				Long ssn = Long.parseLong(sc.nextLine());
				System.out.println("Enter your ADDRESS");
				String address = sc.nextLine();	
				System.out.println("Enter your PHONE number");
				Long contact = Long.parseLong(sc.nextLine());
							

				//Create new Customer
								
				Customer customer = new Customer( first_name, last_name,gender, new Date(), ssn, contact, address);
				
				
				try {
					if(dao.createCustomer(customer)!=0) {
						System.out.println("\n");
					}
				} catch (BusinessException e) {
					System.out.println(e.getMessage());
					
				}
				
				// Get new Customer ID by SSN
				
				Customer newCustomer = dao.getCustomerBySSN(ssn);
				System.out.println("You ID is" + newCustomer.getId());
				
				System.out.println("\nEnter starting balance for your account");
				Double startBalance=sc.nextDouble();
				
				// Generate Account Number
				
			    long leftLimit = 1000000000L;
			    long rightLimit = 9999999999L;
			    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
			    
			    // Create new Account
				
				Account accountCr = new Account(newCustomer.getId(),generatedLong, startBalance, "pending");
				
				try {
					if(dao.createAccount(accountCr)!=0) {
						System.out.println("\nAccount created successfully. Your accout number is: " + accountCr.getAccount_number());
					}
				} catch (BusinessException e) {
					System.out.println(e.getMessage());
					
				}
				
				// Create Login
				
				sc.nextLine();
				System.out.println("Enter new LOGIN");
				String login = sc.nextLine();
				
				System.out.println("Enter new PASSWORD");
				String password = sc.nextLine();
				
				CustomerLogin cusomer_login= new CustomerLogin(newCustomer.getId(), login, password);
				
				try {
					if(dao.createLogin(cusomer_login)!=0) {
						System.out.println("\nAccount created successfully");
					}
				} catch (BusinessException e) {
					System.out.println(e.getMessage());
					
				}
				
				break;


//----------------------------------------------------------------------------------				
//--3----------------------------------Employee--------------------------------------	
				
			case 3:
				System.out.println("Employee log in page");
				int chE = 0;
				
				do {
					System.out.println("EMPLOYEE MENU");
					System.out.println("===========================");
					System.out.println("1)Register new customer");
					System.out.println("2)View all accounts by status");
					System.out.println("3)Change status of an account");

					System.out.println("4)EXIT");
					System.out.println("Please enter appropriate choice between 1-5");
					try {
						chE = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
					}

					switch (chE) {
					
//--3.1---------------------Employee Register new customer-----status active--------------------------------	
					case 1:
						System.out.println("__________________________");
						System.out.println("Register new customer page");
						System.out.println("__________________________");
						
						System.out.println("Enter your FIRST NAME");
						String first_nameE=sc.nextLine();
						System.out.println("Enter your LAST NAME");
						String last_nameE=sc.nextLine();
						System.out.println("Enter F or M for your gender");
						String genderE=sc.nextLine();
						
						
//						System.out.println("Enter your DATE OF BIRTH");
//						sc.useDelimiter(",");
//						String dateString = sc.next();
//					    DateFormat formatter = new SimpleDateFormat("EEEE dd MMM yyyy");
//					    Date dob = formatter.parse(dateString);
//					    System.out.println(dob);
												
				
						System.out.println("Enter your SSN");
						Long ssnE = Long.parseLong(sc.nextLine());
						System.out.println("Enter your ADDRESS");
						String addressE = sc.nextLine();	
						System.out.println("Enter your PHONE number");
						Long contactE = Long.parseLong(sc.nextLine());
									

						//Create new Customer
										
						Customer customerE = new Customer( first_nameE, last_nameE,genderE, new Date(), ssnE, contactE, addressE);
						
						
						try {
							if(dao.createCustomer(customerE)!=0) {
								System.out.println("\n");
							}
						} catch (BusinessException e) {
							System.out.println(e.getMessage());
							
						}
						
						// Get new Customer ID by SSN
						
						Customer newCustomerE = dao.getCustomerBySSN(ssnE);
						System.out.println("You ID is" + newCustomerE.getId());
						
						System.out.println("\nEnter starting balance for your account");
						Double startBalanceE=sc.nextDouble();
						
						// Generate Account Number
						
					    long leftLimitE = 1000000000L;
					    long rightLimitE = 9999999999L;
					    long generatedLongE = leftLimitE + (long) (Math.random() * (rightLimitE - leftLimitE));
					    
					    // Create new Account
						
						Account accountE = new Account(newCustomerE.getId(),generatedLongE, startBalanceE, "active");
						
						try {
							if(dao.createAccount(accountE)!=0) {
								System.out.println("\nAccount created successfully. Your accout number is: " + accountE.getAccount_number());
							}
						} catch (BusinessException e) {
							System.out.println(e.getMessage());
							
						}
						
						// Create Login
						
						sc.nextLine();
						System.out.println("Enter new LOGIN");
						String loginE = sc.nextLine();
						
						System.out.println("Enter new PASSWORD");
						String passwordE = sc.nextLine();
						
						CustomerLogin cusomer_loginE= new CustomerLogin(newCustomerE.getId(), loginE, passwordE);
						
						try {
							if(dao.createLogin(cusomer_loginE)!=0) {
								System.out.println("\nAccount created successfully");
							}
						} catch (BusinessException e) {
							System.out.println(e.getMessage());
							
						}
						
						break;
					
//--3.2----------------------------------View all accounts by Status-------------------------------------	
				
					case 2:
						System.out.println("Enter STATUS");
						String status = sc.nextLine();
						try {
						
						List<Account> accountsList = service.getAccountsByStatus(status);
						if (accountsList!= null) {
							System.out.println("List of accounts with status:  "+ status);
							for(Account a : accountsList) {
								System.out.println(a);
							}
						}
				} catch (NumberFormatException e) {
						System.out.println("Player Id cannot be special characters or symbols or white spaces it is numeric");
				} catch (BusinessException e) {
						System.out.println(e.getMessage());
				}

						break;
						
//--3.3----------------------------------Update status of an account-------------------------------------	
					case 3:
						System.out.println("Enter accout number status of witch you would like to change");
						Long account_numberE = Long.parseLong(sc.nextLine());
						
						System.out.println("Enter new Status");
						String statusE = sc.nextLine();
						
						try {
					
							if(service.updateAccountStatus(statusE, account_numberE)!=0) {
								System.out.println("\nAccount udated successfully");
							} else {
								System.out.println("\nCould not update status");
							};
						
						}catch (BusinessException e) {
							System.out.println(e.getMessage());
						}
					break;
						
//------------------------------------Exit-------------------------------------	
					case 4:
						System.out.println("See you soon");
						
						break;
						
					default:
						System.out.println("Invalid menu option... Kindly Retry................!!!!");
						break;
					}
				} while (chE != 5);
						
				break;
				
			default:
				System.out.println("Invalid menu option... Kindly Retry................!!!!");
				break;
			}
		} while (ch != 4);
	}
}

//		CustomerDAO dao = new CustomerDAOImpl();
//		
//		//Add customer
//		
//		Customer cust = new Customer(70040, "Eli", "Nole","M", new Date(), 9001312321L, 9001312321L, "5678 S Blue Ave");
//		
//		
//		try {
//			if(dao.createCustomer(cust)!=0) {
//				System.out.println("Customer created successfully");
//			}
//		} catch (BusinessException e) {
//			System.out.println(e.getMessage());
//			
//		}
//		
//		// Get customer by id


//		CustomerDAO dao = new CustomerDAOImpl();
//		try {
//			Customer customer= dao.getCustomerById(70032);
//			if(customer!=null) {
//				System.out.println("\nDetails of customerwith id "+customer.getId());
//				System.out.println(customer);
//			}
//		} catch (BusinessException e) {
//			System.out.println(e);
//		}}}
//		
//



