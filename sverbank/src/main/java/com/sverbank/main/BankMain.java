package com.sverbank.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger; // import log

import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;
import com.sverbank.model.Employee;
import com.sverbank.model.Transaction;
import com.sverbank.service.AdministrationService;
import com.sverbank.service.CustomerService;
import com.sverbank.service.LoginService;
import com.sverbank.service.TransactionService;
import com.sverbank.service.impl.AdministrationServiceImpl;
import com.sverbank.service.impl.CustomerServiceImpl;
import com.sverbank.service.impl.LoginServiceImpl;
import com.sverbank.service.impl.TransactionServiceImpl;


public class BankMain {
	
	private static Logger log=Logger.getLogger(BankMain.class);  // Set up log

	public static void main(String[] args) throws BusinessException, ParseException{

		Scanner sc = new Scanner(System.in);

		int ch = 0;
		
		AdministrationService adminService = new AdministrationServiceImpl();
		CustomerService custService = new CustomerServiceImpl();
		TransactionService transService = new TransactionServiceImpl();
		LoginService loginService = new LoginServiceImpl();
		
		do {
			log.info("");
			log.info("==============================");
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
					log.info("Enter your LOGIN");
					String login = sc.nextLine();	
					log.info("Enter your PASSWORD");
					String password = sc.nextLine();	
				
					CustomerLogin customer_login = loginService.letCustomerLogin(login, password);
					int loggedCustomerId = customer_login.getCustomer_id(); // ID of Logged IN Customer
					Customer customer = adminService.getCustomerById(customer_login.getCustomer_id());
					List<Account> accountsList = custService.getAccountsById(customer.getId());
					
					
					if (customer_login != null && customer !=null) {
						log.info("Hello "+ customer.getFirst_name().toUpperCase() +".  Your accounts:");
						log.info("--------------------------------------");
						for(Account a : accountsList) {
							log.info(a.getAccount_number()+"  |  "+a.getStatus()+"  |  $"+a.getBalance());
						}
						//----------------------Customer menu-----------------------------
						int chC = 0;
						
						do {
							log.info("");
							log.info("===========================");
							log.info("     CUSTOMER MENU");
							log.info("===========================");
							log.info("  (1) Withdraw money");
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
									log.info("Enter your account number ) ");
									long account_number = Long.parseLong(sc.nextLine());
									
									//Check if account belongs to logged in customer
									Account sender = custService.getAccountByNumber(account_number);	//run to db
									int accountOwnerId = sender.getCustomer_id();
									if (loggedCustomerId==accountOwnerId) {
									
										// Check status of account
										Account account = custService.getAccountByNumber(account_number);
										if (account.getCustomer_id()==loggedCustomerId && account.getStatus().equals("active")) {
											log.info("How much money would you like to withdraw");
											double withdraw = Double.parseDouble(sc.nextLine());
											double balance = account.getBalance();
											double newBalance = balance - withdraw;

											// update account balance and create transaction
											Transaction transaction = new Transaction("withdraw",account_number,1000000000, withdraw, new Date());
											int wentThrough = transService.cashOperation(transaction, account_number, newBalance);
											if (wentThrough!= 0 && wentThrough!=1 ) {
												log.info("Money withdrawed successfully. Availible balance is: $ "+newBalance);
											}else {
												log.info("Could not withdraw money");}
										}else {
											log.info("Wrong account number or your account is not active");
										};
									}else {
										log.info("This account does not belong to " +customer.getFirst_name());};
								} catch (BusinessException e) {
									log.info(e.getMessage());
								}
							break;
							
							//--1.2------------------------ Add money ----------------------------------		
							
							case 2:
								try {
									log.info("Enter your account number) ");
									long account_number = Long.parseLong(sc.nextLine());
									
									//Check if account belongs to logged in customer
									Account sender = custService.getAccountByNumber(account_number);	//run to db
									int accountOwnerId = sender.getCustomer_id();
									if (loggedCustomerId==accountOwnerId) {
									
										// Check status of account
										Account account = custService.getAccountByNumber(account_number);
										if (account.getCustomer_id()==loggedCustomerId && account.getStatus().equals("active")) {
											log.info("How much money would you like to add");
											double add = Double.parseDouble(sc.nextLine());
											double balance = account.getBalance();
											double newBalance = balance + add;
											// update account balance and create transaction
											Transaction transaction = new Transaction("add funds", 1000000000, account_number, add, new Date());
											int wentThrough = transService.cashOperation(transaction, account_number, newBalance);
											if (wentThrough!= 0 && wentThrough!=1 ) {
												log.info("Funds added successfully. Availible balance is: $ "+newBalance);
											}else {
												log.info("Could not add funds");}
										}else {
											log.info("You account is not active");
										};
									}else {
										log.info("This account does not belong to " +customer.getFirst_name());};	
								} catch (BusinessException e) {
									log.info(e.getMessage());
								}
							break;
							
							//--1.3-------------------Transfer money ----------------------------------		
							
							case 3:
								
								log.info("Enter account number you would like to send money from) ");
								long  sender_acc_num = Long.parseLong(sc.nextLine());
																
								//Check if account belongs to logged in customer
								Account sender = custService.getAccountByNumber(sender_acc_num);	//run to db
								int accountOwnerId = sender.getCustomer_id();
								if (loggedCustomerId==accountOwnerId) {

									//Check if account has an active status
									if (sender.getStatus().equals("active")) {

										log.info("Enter recipient account number) ");
										long  receiver_acc_num = Long.parseLong(sc.nextLine());
										//Check if sender and recipient are different accounts
										if (sender_acc_num != receiver_acc_num) {
											//Validate recipient account
											Account receiver = custService.getAccountByNumber(receiver_acc_num); //run to db
											if (receiver.getStatus().equals("active")) {
												log.info("How much money would you like to send");
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
														transService.createTransactionTransfer (transfer,sender_acc_num,newBalance);								//run to db
														log.info("Money send successfully");
														log.info("Wait for recipient to approve transfer");
													} catch (BusinessException e) {
														log.info(e.getMessage());
													}

												} else 
													{log.info("You can not send less then $1 or more then your available funds $"+sender.getBalance());};
											} else 
												{log.info("Recipient account does not accept transfers at the moment");};
										} else {
											log.info("You can not send money to the same account");};
									} else {
										log.info("This account is not in active status");};
								}else {
									log.info("This account does not belong to " +customer.getFirst_name());};
								
							break;
							
							//--1.4------------------------Receive money ----------------------------------		
							
							case 4:
								try {
									log.info("Enter your account number ");
									long receiver_acc_num = Long.parseLong(sc.nextLine());
									
									//Validate account 
									Account receiver = custService.getAccountByNumber(receiver_acc_num);	//run to db
									int enteredAccountId = receiver.getCustomer_id();
									if (loggedCustomerId==enteredAccountId) {
									
									// Check status of account
									List<Transaction> transfersList = custService.getTtransfersByAccNumber(receiver_acc_num);

										if (transfersList != null) {

											for (Transaction t : transfersList) {
												log.info("Awaiting transfer : ");
												log.info("--------------------------------------");
												log.info("$"+t.getAmount()+" | from: "+t.getSender_acc_num()+ " | on "+t.getDate());
												
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
													double amount = t.getAmount();
													switch (chTr) {
													//---------------Accept transfer-------------
													case 1:
														try {
															Double balance = custService.getAccountByNumber(receiver_acc_num).getBalance();
															Double newBalance = balance + amount;
															String type = "approved transfer";
															
															if(transService.processTransfer(newBalance, receiver_acc_num, transaction_id,type)!=0 && transService.processTransfer(newBalance, receiver_acc_num, transaction_id,type)!=1) {
															log.info("Money trunsfered to your account sccessfully");
															log.info("Availible balance is $"+newBalance);
															} else {
																log.info("Could not process transfer");
															}
															
														} catch (BusinessException e) {
															log.info(e.getMessage());
															
														} finally {
															chTr=3;
														}
												break;
												
													//---------------Decline transfer------------
												case 2:

														try {
															Double balance = custService.getAccountByNumber(sender_acccount).getBalance();
															
															Double newBalance = balance + amount;
															String type = "declined transfer";
															if(transService.processTransfer(newBalance, sender_acccount, transaction_id,type)>=2) {
															log.info("Money returned to sender sccessfully");
														} else {
															log.info("Could not process transfer");
														}
															
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
													log.info("Invalid menu option. Please try again");
													break;
												}
											} while (chTr != 3);}
											
										}else {
											log.info("You do not have awaiting transfers");
											}
									}else {
										log.info("Wrong account number");
										}
											

								} catch (BusinessException e) {
									log.info(e.getMessage());
								}
							break;
								
							//--1.5----------------------Apply for new account ----------------------------------		
							
							case 5:
								try {
								
									log.info("Enter starting balance for your account");
									Double startBalance=Double.parseDouble(sc.nextLine());
							
									// Generate Account Number
									long leftLimit = 1000000000L;
									long rightLimit = 9999999999L;
									long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
						    
									// Create new Account
									Account newAccount = new Account(customer.getId(),generatedLong, startBalance, "pending");

									if(custService.createAccount(newAccount)!=0) {
										log.info("Registration complited successfully");
										log.info("Your new accoutn " + newAccount.getAccount_number()+" is pending");
									} else { log.info("Application did not go through");}
								}catch (BusinessException e) {
									log.info(e.getMessage());
								}
							
							break;
							//--1.6------------------------ Exit ----------------------------------		
								
							case 6:
							break;
							
							default:
							log.info("Invalid menu option.Please try again!");
							break;
						}
							}while (chC != 6);
					}
			} catch (NumberFormatException e) {
					log.info("Numeric only. No characters, symbols or white spaces are accepted");
			} catch (BusinessException e) {
					log.info(e.getMessage());
			}


			

				break;	
			
//--2---------------------------New Customer Registration-------pending status------------------------------	
		
			case 2:
				try {

					log.info("Enter your FIRST NAME");
					String first_name=sc.nextLine();
					log.info("nter your LAST NAME");
					String last_name=sc.nextLine();
					log.info("Enter F or M for your gender");
					String gender=sc.nextLine();
					
					// Date formatting
					log.info("Enter your DOB in format yyyy-dd-mm");
					String s = sc.nextLine();
			    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
			    	sdf.setLenient(false);
			    	Date date = null;
			    	try {
			    		date = sdf.parse(s);
			    	}catch (ParseException e1){
			    		log.info("Invalid date format");
			    	}
			    	
					log.info("Enter your SSN (9 digits without space)");
					Long ssn = Long.parseLong(sc.nextLine());
					log.info("Enter your ADDRESS");
					String address = sc.nextLine();	
					log.info("Enter your PHONE number(10 digits without space)");
					Long contact = Long.parseLong(sc.nextLine());

					//Create new Customer
					Customer customer = new Customer( first_name, last_name,gender, date, ssn, contact, address);

					if(custService.createCustomer(customer)!=0) {
						
						// Create Login
						int chL = 0;
						do {
						
						log.info("Enter new LOGIN (4-12 characters)");
						String login = sc.nextLine();
						log.info("Enter new PASSWORD (4-12 characters)");
						String password = sc.nextLine();
						
						// Get new Customer ID by SSN
						int id = adminService.getCustomerBySSN(ssn).getId();
						CustomerLogin customer_login= new CustomerLogin(id, login, password);

							if(custService.createLogin(customer_login)!=0) {
				
								log.info("Enter starting balance for your account");
								Double startBalance=sc.nextDouble();
								
								// Generate Account Number
								long leftLimit = 1000000000L;
							    long rightLimit = 9999999999L;
							    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
							    
							    // Create new Account
								Account newAccount = new Account(id,generatedLong, startBalance, "pending");
								try {
									if(custService.createAccount(newAccount)!=0) {
										log.info("Registration complited successfully. Now you can log in");
										log.info("Your accoutn " + newAccount.getAccount_number()+" is pending");
										chL++;
									}
								} catch (BusinessException e) {
									log.info(e.getMessage());
								}
							}
					} while (chL!=1);}
				} catch (BusinessException e) {
					log.info(e.getMessage());
				}
				
			break;


//----------------------------------------------------------------------------------				
//--3----------------------------------Employee--------------------------------------	
				
			case 3:
				try {
				log.info("Enter your EMPLOYEE ID");
				String employee_id=sc.nextLine();
				log.info("Enter your PASSWORD");
				String password = sc.nextLine();

				Employee employee = loginService.employeeLogin(employee_id, password);
				
				if (employee !=null) {
					log.info("You logged in as "+ employee.getName());
			
					int chE = 0;
				
					do {
						log.info("");
						log.info("===========================");;
						log.info("       EMPLOYEE MENU");
						log.info("===========================");
						log.info("  (1)  Customer registration");
						log.info("  (2)  List of accounts by status");
						log.info("  (3)  Change status of an account");
						log.info("  (4)  List of transactions");
						log.info("  (5)  List of customers");
						log.info("  (6)  Find a customer");

						log.info("  (7)  EXIT");

				
						chE = Integer.parseInt(sc.nextLine());


						switch (chE) {

//--3.1---------------------Employee Register new customer-----status active--------------------------------	
						case 1:

							log.info("Enter customer's FIRST NAME");
							String first_nameE=sc.nextLine();
							log.info("Enter customer's LAST NAME");
							String last_nameE=sc.nextLine();
							log.info("Enter F or M for customer's gender");
							String genderE=sc.nextLine();
	
							// Date formatting
							log.info("Enter customer's DATE OF BIRTH in format yyyy-dd-mm");
							String s = sc.nextLine();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
							sdf.setLenient(false);
							Date date = null;
							try {
								date = sdf.parse(s);
							}catch (ParseException e1){
								log.info("Invalid date format");
							}
												
							log.info("Enter SSN (9 digits without space))");
							Long ssnE = Long.parseLong(sc.nextLine());
							log.info("Enter customer's ADDRESS");
							String addressE = sc.nextLine();	
							log.info("Enter customer's PHONE number (10 digits without spaces)");
							Long contactE = Long.parseLong(sc.nextLine());
									

							//Create new Customer
							Customer customerE = new Customer( first_nameE, last_nameE,genderE, date, ssnE, contactE, addressE);
							try {
								if(custService.createCustomer(customerE)!=0) {
									// Get new Customer ID by SSN
									Customer newCustomerE = adminService.getCustomerBySSN(ssnE);
									log.info( "Profile with  ID " + newCustomerE.getId()+" created succsessfully");
								
									log.info("Enter starting balance for an account");
									Double startBalanceE=sc.nextDouble();
								
									// Generate Account Number
									long leftLimitE = 1000000000L;
									long rightLimitE = 9999999999L;
									long generatedLongE = leftLimitE + (long) (Math.random() * (rightLimitE - leftLimitE));
							    
									// Create new Account
									Account accountE = new Account(newCustomerE.getId(),generatedLongE, startBalanceE, "active");
								
									try {
										if(custService.createAccount(accountE)!=0) {
											log.info("Account "+ accountE.getAccount_number()+" created successfully" );
											// Create Login
											sc.nextLine();
											log.info("Enter new LOGIN");
											String loginE = sc.nextLine();
										
											log.info("Enter new PASSWORD");
											String passwordE = sc.nextLine();
										
											CustomerLogin cusomer_loginE= new CustomerLogin(newCustomerE.getId(), loginE, passwordE);
										
											try {
												if(custService.createLogin(cusomer_loginE)!=0) {
													log.info("Login created successfully");
													break;
												} else {
													log.info("Could not create login");
												};
											} catch (BusinessException e) {
												log.info(e.getMessage());
												break;
											}
										}
									} catch (BusinessException e) {
										log.info(e.getMessage());
										break;
									}
								

								}
							} catch (BusinessException e) {
								log.info(e.getMessage());
		
								break;
							}
						

						

					
//--3.2----------------------------------View all accounts by Status-------------------------------------	
				
						case 2:
							log.info("Enter STATUS");
							String status = sc.nextLine();
							try {
						
								List<Account> accountsList = adminService.getAccountsByStatus(status);
								if (accountsList!= null) {
									log.info("List of accounts with status:  "+ status);
									for(Account a : accountsList) {
										log.info(a);
									}
								}
							} catch (NumberFormatException e) {
								log.info("  Player Id cannot be special characters or symbols or white spaces it is numeric");
							} catch (BusinessException e) {
								log.info(e.getMessage());
							}

						break;
						
//--3.3----------------------------------Update status of an account by number-------------------------------------	
						case 3:
							log.info("  Enter accout number status of witch you would like to change");
							Long account_numberE = Long.parseLong(sc.nextLine());
						
							log.info("  Enter new Status");
							String statusE = sc.nextLine();
						
							try {
					
								if(adminService.updateAccountStatus(statusE, account_numberE)!=0) {
									log.info("  Account udated successfully");
								} else {
									log.info("  Could not update status");
								};
						
							}catch (BusinessException e) {
								log.info(e.getMessage());
							}
						break;
//--3.4-------------------------------See all transactions  -------------------------------	
						case 4:
							try {
								List<Transaction> transactionList = adminService. getAllTransactions();
								if(transactionList!=null) {
									log.info("");
									log.info("  All bank transactions:");
									log.info("-----------------------------");
									for (Transaction t : transactionList) {
										log.info(t);
									}
								} else {
									log.info("  Could access transactions list");
								};
						
							}catch (BusinessException e) {
								log.info(e.getMessage());
							}
						
						break;
						
//-3.5-------------------------------See All Customers--------------------------------	
						case 5:
							try {
								List<Customer> customersList = adminService.getAllCustomers();
								if(customersList!=null) {
									log.info("");
									log.info("  All customers:");
									log.info("-----------------------------");
									for (Customer c : customersList) {
										log.info(c);
									}
								} else {
									log.info("");
									log.info("  Could access customers list");
								};
						
							}catch (BusinessException e) {
								log.info(e.getMessage());
							}
						
						break;
//-3.6-------------------------------Find A Customers--------------------------------------	
						case 6:
							
							int chFind = 0;
							
							do {
								log.info("");
								log.info("===========================");
								log.info("     CUSTOMER SEARCH");
								log.info("===========================");
								log.info("  (1) By ID");
								log.info("  (2) By SSN");
								log.info("  (3) EXIT");
								
								try {
									chFind = Integer.parseInt(sc.nextLine());
								} catch (NumberFormatException e) {
								}
								switch (chFind) {
								
								//--3.6.1----------------------------- Customer by ID -----------------------------

								case 1:
									try {
										log.info("  Enter customer ID");
										int id = Integer.parseInt(sc.nextLine());
										Customer customer =adminService.getCustomerById(id);
										if(customer!=null) {
											log.info(customer);
										} else {
											log.info(""); log.info("  Could access customer info");
										};
								
									}catch (BusinessException e) {
										log.info(e.getMessage());
									}
							
									break;	
									
									//--3.6.2----------------------------- Customer by SSN -----------------------------

									case 2:
										try {
											log.info("  Enter customer SSN");
											long ssn = Long.parseLong(sc.nextLine());
											Customer customer =adminService.getCustomerBySSN(ssn);
											if(customer!=null) {
												log.info(customer);
											} else {
												log.info("  Could access customer info");
											};
									
										}catch (BusinessException e) {
											log.info(e.getMessage());
										}
								
										break;	
								default:
									log.info("Invalid menu option.Please try again!");
									break;
								}
							} while (chE != 3);
						
//-3.7-------------------------------------------Exit-------------------------------------	
						case 7:
							log.info("  See you soon");
						
						break;						
						default:
							log.info("  Invalid menu option.Please try again!");
						break;
						}
					} while (chE != 7);
				} else {
					log.info("Wrong employee id or password");
				}
			} catch (BusinessException e) {
				log.info(e.getMessage());
				}
					
			default:
				log.info("  Invalid menu option.Please try again!");
				break;
			}
		} while (ch != 4);
		
		sc.close(); // avoiding resource leak
		
	}
}
		
	





