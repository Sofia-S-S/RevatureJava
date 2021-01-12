package com.sverbank.main;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger; // import log


import com.sverbank.dao.CustomerDAO;
import com.sverbank.dao.LoginDAO;
import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.dao.impl.LoginDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;
import com.sverbank.model.Employee;
import com.sverbank.model.Transaction;
import com.sverbank.service.CustomerService;
import com.sverbank.service.LoginService;
import com.sverbank.service.impl.CustomerServiceImpl;
import com.sverbank.service.impl.LoginServiceImpl;


public class BankMain {
	
	private static Logger log=Logger.getLogger(BankMain.class);  // Set up log



	public static void main(String[] args) throws BusinessException, ParseException{


	
		Scanner sc = new Scanner(System.in);



		int ch = 0;
		
		CustomerDAO dao = new CustomerDAOImpl();
		CustomerService service = new CustomerServiceImpl();
		LoginDAO loginDAO = new LoginDAOImpl();
		LoginService loginService = new LoginServiceImpl();
		
		do {
			log.info("\n==============================");
			log.info("    WELCOME TO SVERBANK");
			log.info("==============================");
			log.info("  (1) Log in to my account");
			log.info("  (2) New Customer");
			log.info("  (3) Employee log in");
			
			try {
				ch = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
			}

			switch (ch) {
			
//--1------------------------------------Customer log in-------------------------------------	
			
			case 1:
				try {
					log.info("  Enter your LOGIN");
					String login = sc.nextLine();	
					log.info("  Enter your PASSWORD");
					String password = sc.nextLine();	
				
					CustomerLogin customer_login = loginService.letCustomerLogin(login, password);
					int loggedCustomerId = customer_login.getCustomer_id(); // ID of Logged IN Customer
					Customer customer = service.getCustomerById(customer_login.getCustomer_id());
					List<Account> accountsList = service.getAccountsById(customer.getId());
					
					
					if (customer_login != null && customer !=null) {
						log.info("	Hello "+ customer.getFirst_name().toUpperCase() +".  Your accounts:\n");
						log.info("--------------------------------------");
						for(Account a : accountsList) {
							log.info(a.getAccount_number()+"  |  "+a.getStatus()+"  |  $"+a.getBalance());
						}
						//----------------------Customer menu-----------------------------
						int chC = 0;
						
						do {
							log.info("\n===========================");
							log.info("     CUSTOMER MENU");
							log.info("===========================");
							log.info("	(1) Withdraw money");
							log.info("  (2) Add funds");
							log.info("  (3) Send money");
							log.info("  (4) Receive money");
							log.info("  (5) Apply for new account");
							log.info("  (6)	EXIT");
							
							try {
								chC = Integer.parseInt(sc.nextLine());
							} catch (NumberFormatException e) {
							}
							
							//--1.1----------------------------- Withdraw money -----------------------------

							switch (chC) {
							case 1:
								try {
									log.info("	Enter your account number ) ");
									long account_number = Long.parseLong(sc.nextLine());
									
									//Check if account belongs to logged in customer
									Account sender = service.getAccountByNumber(account_number);	//run to db
									int accountOwnerId = sender.getCustomer_id();
									if (loggedCustomerId==accountOwnerId) {
									
										// Check status of account
										Account account = service.getAccountByNumber(account_number);
										if (account.getCustomer_id()==loggedCustomerId && account.getStatus().equals("active")) {
											log.info("	How much money would you like to withdraw");
											double transfer = Double.parseDouble(sc.nextLine());
											double balance = account.getBalance();
											double newBalance = balance - transfer;
											// update account balance
											Account acc = service.updateAccountBalance(account_number, newBalance);
											if (acc != null) {
												log.info("\n  Money withdrawed successfully. Availible balance is: $ "+newBalance);
											}else {
												log.info("\n  Could not withdraw money");}
										}else {
											log.info("\n  Wrong account number or your account is not active");
										};
									}else {
										log.info("  This account does not belong to " +customer.getFirst_name());};
								} catch (BusinessException e) {
									log.info(e.getMessage());
								}
							break;
							
							//--1.2------------------------ Add money ----------------------------------		
							
							case 2:
								try {
									log.info("  Enter your account number) ");
									long account_number = Long.parseLong(sc.nextLine());
									
									//Check if account belongs to logged in customer
									Account sender = service.getAccountByNumber(account_number);	//run to db
									int accountOwnerId = sender.getCustomer_id();
									if (loggedCustomerId==accountOwnerId) {
									
										// Check status of account
										Account account = service.getAccountByNumber(account_number);
										if (account.getCustomer_id()==loggedCustomerId && account.getStatus().equals("active")) {
											log.info("  How much money would you like to add");
											double transfer = Double.parseDouble(sc.nextLine());
											double balance = account.getBalance();
											double newBalance = balance + transfer;
											// update account balance
											Account acc = service.updateAccountBalance(account_number, newBalance);
											if (acc != null) {
												log.info("\n Funds added successfully. Availible balance is: $ "+newBalance);
											}else {
												log.info("\n  Could not add funds");}
										}else {
											log.info("\n  You account is not active");
										};
									}else {
										log.info("  This account does not belong to " +customer.getFirst_name());};	
								} catch (BusinessException e) {
									log.info(e.getMessage());
								}
							break;
							
							//--1.3-------------------Transfer money ----------------------------------		
							
							case 3:
								
								log.info("  Enter account number you would like to send money from) ");
								long  sender_acc_num = Long.parseLong(sc.nextLine());
																
								//Check if account belongs to logged in customer
								Account sender = service.getAccountByNumber(sender_acc_num);	//run to db
								int accountOwnerId = sender.getCustomer_id();
								if (loggedCustomerId==accountOwnerId) {
									log.info("It is your account - passed");
									//Check if account has an active status
									if (sender.getStatus().equals("active")) {
										log.info("Account is active - passed");
										log.info("  Enter recipient account number) ");
										long  receiver_acc_num = Long.parseLong(sc.nextLine());
										//Check if sender and recipient are different accounts
										if (sender_acc_num != receiver_acc_num) {
											//Validate recipient account
											Account receiver = service.getAccountByNumber(receiver_acc_num); //run to db
											if (receiver.getStatus().equals("active")) {
												log.info("  How much money would you like to send");
												double balance = sender.getBalance();
												double amount = Double.parseDouble(sc.nextLine());
												//Check amount of money to send and available balance
												if (amount>0 && amount<sender.getBalance()) {
													//Take money from account
													Date date = new Date();
													String type = "transfer";
													
													//Create new Transfer
													Transaction transfer = new Transaction( type, sender_acc_num, receiver_acc_num, amount, date);
													double newBalance = balance -amount;
													//Subtract from balance (customer) && add to amount (transaction - transfer)			
													try {
														dao.createTransactionTransfer (transfer,sender_acc_num,newBalance);								//run to db
														log.info("  Money send successfully. Wait fot recipient to approve transfer");
														
													} catch (BusinessException e) {
														log.info(e.getMessage());
													}

												} else 
													{log.info("  You can not send less then $1 or more then your available funds $"+sender.getBalance());};
											} else 
												{log.info("  Recipient account does not accept transfers at the moment");};
										} else {
											log.info("  You can not send money to the same account");};
									} else {
										log.info("  This account is not in active status");};
								}else {
									log.info("  This account does not belong to " +customer.getFirst_name());};
								
							break;
							
							//--1.4------------------------Receive money ----------------------------------		
							
							case 4:
								try {
									log.info("  Enter your account number ");
									long receiver_acc_num = Long.parseLong(sc.nextLine());
									
									//Validate account 
									Account receiver = service.getAccountByNumber(receiver_acc_num);	//run to db
									int enteredAccountId = receiver.getCustomer_id();
									if (loggedCustomerId==enteredAccountId) {
									
									// Check status of account
									List<Transaction> transfersList = dao.getTtransfersByAccNumber(receiver_acc_num);

										if (transfersList != null) {
											log.info("\n  Your awaiting trunsfers");
											log.info("--------------------------------------");
											for (Transaction t : transfersList) {
												log.info("  $"+t.getAmount()+" | from: "+t.getSender_acc_num()+ " | on "+t.getDate());
												
												//---------------------transfer menu------------------
												int chTr=0;
												do {
													
													log.info("--------------------------------------");
													log.info("  (1) Accept transfer");
													log.info("  (2) Decline trunsfer");
													log.info("  (3) Exit /NEXT transfer");
													try {
														chTr = Integer.parseInt(sc.nextLine());
													} catch (NumberFormatException e) {
													}
													
													long transaction_id = t.getTransaction_id();
													long sender_acccount = t.getSender_acc_num();
													switch (chTr) {
													//---------------Accept transfer-------------
													case 1:
														try {
															Double balance = dao.getAccountByNumber(receiver_acc_num).getBalance();
															Double amount = dao.getTransactionById(transaction_id).getAmount();
															Double newBalance = balance + amount;
															String type = "Approved Transfer";
															
															dao.approveTransfer(newBalance, receiver_acc_num, transaction_id,type);
															log.info("\n  Money trunsfered to your account sccessfully");
															log.info("\n  Availible balance is $"+newBalance);
															
														} catch (BusinessException e) {
															log.info(e.getMessage());
															
														} finally {
															chTr=3;
														}
												break;
												
													//---------------Decline transfer------------
												case 2:

														try {
															Double balance = dao.getAccountByNumber(sender_acccount).getBalance();
															Double amount = dao.getTransactionById(transaction_id).getAmount();
															Double newBalance = balance + amount;
															dao.approveTransfer(newBalance, sender_acccount, transaction_id,"Declined Transfer");
															log.info("\n  Money returned to sender sccessfully");
													
															
														} catch (BusinessException e) {
															log.info(e.getMessage());
															
														}finally {
															chTr=3;
														}
													break;
													//---------------Exit------------------------
													case 3:
												break;
													
												default:
													log.info("\n Invalid menu option. Please try again");
													break;
												}
											} while (chTr != 3);}
											
										}else {
											log.info("\n  You do not have awaiting transfers");
											}
									}else {
										log.info("\n  Wrong account number");
										}
											

								} catch (BusinessException e) {
									log.info(e.getMessage());
								}
							break;
								
							//--1.5----------------------Apply for new account ----------------------------------		
							
							case 5:
								try {
								
									log.info("\n  Enter starting balance for your account");
									Double startBalance=sc.nextDouble();
							
									// Generate Account Number
									long leftLimit = 1000000000L;
									long rightLimit = 9999999999L;
									long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
						    
									// Create new Account
									Account newAccount = new Account(customer.getId(),generatedLong, startBalance, "pending");

									if(dao.createAccount(newAccount)!=0) {
										log.info("\n  Registration complited successfully. Now you can log in");
										log.info("  Your accoutn " + newAccount.getAccount_number()+" is pending");
									} else { log.info("\nApplication did not go through");}
								}catch (BusinessException e) {
									log.info(e.getMessage());
								}
							
							break;
							//--1.6------------------------ Exit ----------------------------------		
								
							case 6:
							break;
						}
							}while (chC != 6);
					}
			} catch (NumberFormatException e) {
					log.info("Player Id can not be special characters or symbols or white spaces it is numeric");
			} catch (BusinessException e) {
					log.info(e.getMessage());
			}


			

				break;	
			
//--2---------------------------New Customer Registration-------pending status------------------------------	
		
			case 2:
				try {

					log.info("\n  Enter your FIRST NAME");
					String first_name=sc.nextLine();
					log.info("\n Enter your LAST NAME");
					String last_name=sc.nextLine();
					log.info("\n  Enter F or M for your gender");
					String gender=sc.nextLine();
					
					// Date formatting
					log.info("\n  Enter your DATE OF BIRTH in format yyyy-dd-mm");
					String s = sc.nextLine();
			    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
			    	sdf.setLenient(false);
			    	Date date = null;
			    	try {
			    		date = sdf.parse(s);
			    	}catch (ParseException e1){
			    		log.info("Invalid date format");
			    	}

					log.info("Enter your SSN");
					Long ssn = Long.parseLong(sc.nextLine());
					log.info("Enter your ADDRESS");
					String address = sc.nextLine();	
					log.info("Enter your PHONE number");
					Long contact = Long.parseLong(sc.nextLine());

					//Create new Customer
					Customer customer = new Customer( first_name, last_name,gender, date, ssn, contact, address);

					if(service.createCustomer(customer)!=0) {
						
						// Create Login
						log.info("\n  Enter new LOGIN");
						String login = sc.nextLine();
						log.info("\n  Enter new PASSWORD");
						String password = sc.nextLine();
						
						// Get new Customer ID by SSN
						int id = dao.getCustomerBySSN(ssn).getId();
						CustomerLogin customer_login= new CustomerLogin(id, login, password);

							if(dao.createLogin(customer_login)!=0) {
				
								log.info("\n  Enter starting balance for your account");
								Double startBalance=sc.nextDouble();
								
								// Generate Account Number
								long leftLimit = 1000000000L;
							    long rightLimit = 9999999999L;
							    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
							    
							    // Create new Account
								Account newAccount = new Account(id,generatedLong, startBalance, "pending");

									if(dao.createAccount(newAccount)!=0) {
										log.info("\n  Registration complited successfully. Now you can log in");
										log.info("\n  Your accoutn " + newAccount.getAccount_number()+" is pending");
									}
							}
					}
				} catch (BusinessException e) {
					log.info(e.getMessage());
				}
				
			break;


//----------------------------------------------------------------------------------				
//--3----------------------------------Employee--------------------------------------	
				
			case 3:
				try {
				log.info("  Enter your EMPLOYEE ID");
				String employee_id=sc.nextLine();
				log.info("  Enter your PASSWORD");
				String password = sc.nextLine();

				Employee employee = loginService.employeeLogin(employee_id, password);
				
				if (employee !=null) {
					log.info("	Hello "+ employee.getName());
			
					int chE = 0;
				
					do {
						log.info("\n===========================");;
						log.info("       EMPLOYEE MENU");
						log.info("===========================");
						log.info("  (1)  Register new customer");
						log.info("  (2)  View all accounts by status");
						log.info("  (3)  Change status of an account");

						log.info("  (4)  EXIT");

				
						chE = Integer.parseInt(sc.nextLine());


						switch (chE) {

//--3.1---------------------Employee Register new customer-----status active--------------------------------	
						case 1:

							log.info("  Enter customer's FIRST NAME");
							String first_nameE=sc.nextLine();
							log.info("  Enter customer's LAST NAME");
							String last_nameE=sc.nextLine();
							log.info("  Enter F or M for customer's gender");
							String genderE=sc.nextLine();
	
							// Date formatting
							log.info("  Ente customer'ss DATE OF BIRTH in format yyyy-dd-mm");
							String s = sc.nextLine();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
							sdf.setLenient(false);
							Date date = null;
							try {
								date = sdf.parse(s);
							}catch (ParseException e1){
								log.info("Invalid date format");
							}
												
							log.info("  Enter SSN (9 digits without space))");
							Long ssnE = Long.parseLong(sc.nextLine());
							log.info("  Enter customer's ADDRESS");
							String addressE = sc.nextLine();	
							log.info("  Enter customer's PHONE number (10 digits without spaces)");
							Long contactE = Long.parseLong(sc.nextLine());
									

							//Create new Customer
							Customer customerE = new Customer( first_nameE, last_nameE,genderE, date, ssnE, contactE, addressE);
							try {
								if(dao.createCustomer(customerE)!=0) {
									log.info("\n");
								}
							} catch (BusinessException e) {
								log.info(e.getMessage());
							
							}
						
							// Get new Customer ID by SSN
							Customer newCustomerE = dao.getCustomerBySSN(ssnE);
							log.info( "  Profile with  ID " + newCustomerE.getId()+" created succsessfully");
						
							log.info("\n   Enter starting balance forann account");
							Double startBalanceE=sc.nextDouble();
						
							// Generate Account Number
							long leftLimitE = 1000000000L;
							long rightLimitE = 9999999999L;
							long generatedLongE = leftLimitE + (long) (Math.random() * (rightLimitE - leftLimitE));
					    
							// Create new Account
							Account accountE = new Account(newCustomerE.getId(),generatedLongE, startBalanceE, "active");
						
							try {
								if(dao.createAccount(accountE)!=0) {
									log.info("\n   Account"+ accountE.getAccount_number()+" created successfully" );
								}
							} catch (BusinessException e) {
								log.info(e.getMessage());
							
							}
						
							// Create Login
							sc.nextLine();
							log.info("  Enter new LOGIN");
							String loginE = sc.nextLine();
						
							log.info("  Enter new PASSWORD");
							String passwordE = sc.nextLine();
						
							CustomerLogin cusomer_loginE= new CustomerLogin(newCustomerE.getId(), loginE, passwordE);
						
							try {
								if(dao.createLogin(cusomer_loginE)!=0) {
									log.info("\n Loginn created successfully");
								} else {
									log.info("\n  Could not createloginn");
								};
							} catch (BusinessException e) {
								log.info(e.getMessage());
							
							}
						
						break;
					
//--3.2----------------------------------View all accounts by Status-------------------------------------	
				
						case 2:
							log.info("  Enter STATUS");
							String status = sc.nextLine();
							try {
						
								List<Account> accountsList = service.getAccountsByStatus(status);
								if (accountsList!= null) {
									log.info("List of accounts with status:  "+ status);
									for(Account a : accountsList) {
										log.info(a);
									}
								}
							} catch (NumberFormatException e) {
								log.info("Player Id cannot be special characters or symbols or white spaces it is numeric");
							} catch (BusinessException e) {
								log.info(e.getMessage());
							}

						break;
						
//--3.3----------------------------------Update status of an account-------------------------------------	
						case 3:
							log.info("  Enter accout number status of witch you would like to change");
							Long account_numberE = Long.parseLong(sc.nextLine());
						
							log.info("  Enter new Status");
							String statusE = sc.nextLine();
						
							try {
					
								if(service.updateAccountStatus(statusE, account_numberE)!=0) {
									log.info("\n  Account udated successfully");
								} else {
									log.info("\n  Could not update status");
								};
						
							}catch (BusinessException e) {
								log.info(e.getMessage());
							}
						break;
//--3.4-------------------------------See all transactions  -------------------------------	
						case 4:
							log.info("See you soon");
						
						break;
						
//-3.5-------------------------------------------Exit-------------------------------------	
						case 5:
							log.info("See you soon");
						
						break;
						
						default:
							log.info("Invalid menu option.Please try again!");
						break;
						}
					} while (chE != 5);
				} else {
					log.info("Wrong employee id or password");
				}
			} catch (BusinessException e) {
				log.info(e.getMessage());
				}
					
			default:
				log.info("Invalid menu option.Please try again!");
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
//				log.info("Customer created successfully");
//			}
//		} catch (BusinessException e) {
//			log.info(e.getMessage());
//			
//		}
//		
//		// Get customer by id


//		CustomerDAO dao = new CustomerDAOImpl();
//		try {
//			Customer customer= dao.getCustomerById(70032);
//			if(customer!=null) {
//				log.info("\nDetails of customerwith id "+customer.getId());
//				log.info(customer);
//			}
//		} catch (BusinessException e) {
//			log.info(e);
//		}}}
//		
//



