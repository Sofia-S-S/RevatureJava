package com.sverbank.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.sverbank.dao.impl.AdministrationDAOImpl;
import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.dao.impl.LoginDAOImpl;
import com.sverbank.dao.impl.TransactionDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.service.impl.AppServiceImpl;

public class AppMockitoTest {
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
    @InjectMocks  
    AppServiceImpl appService;
  
    @Mock  
	AdministrationDAOImpl adminMock;
    
    @Mock      
    CustomerDAOImpl custMock; 
    
    @Mock  
    LoginDAOImpl logMock;
    
    @Mock  
	TransactionDAOImpl transMock;
    
 //----------------------------------Administration--------------------------------------------------
    
    @Test
	public void getCustomerBySSN() throws BusinessException {
    	
    	boolean b = appService.getCustomerBySSN(884645367L);
    	assertEquals(true, b);
    	boolean b1 = appService.getCustomerBySSN(8846453L);
    	assertEquals(false, b1);
    	
    	verify(adminMock, times(1)).getCustomerBySSN(884645367L); 
    	
    	verify(adminMock, times(1)).getCustomerBySSN(8846453L); 
	}
    
    @Test
    public void getCustomerById() throws BusinessException {
    	
    	boolean b = appService.getCustomerById(12);
    	assertEquals(false, b);
    	verify(adminMock, times(1)).getCustomerById(12); 
    	
    	boolean b1 = appService.getCustomerById(55555);
    	assertEquals(true, b1);
    	verify(adminMock, times(1)).getCustomerById(55555); 
    }
    @Test
    public void getAccountsByStatus() throws BusinessException {
    	boolean b = appService.getAccountsByStatus("active");
    	assertEquals(true, b);
    	verify(adminMock, times(1)).getAccountsByStatus("active");
    	
    	boolean b1 = appService.getAccountsByStatus("notactive");
    	assertEquals(false, b1);
    	verify(adminMock, times(1)).getAccountsByStatus("notactive");
    }
    @Test
    public void  updateAccountStatus()throws BusinessException {
    	boolean b = appService.updateAccountStatus("active", 9994999969L);
    	assertEquals(true, b);
    	verify(adminMock, times(1)).updateAccountStatus("active", 9994999969L);
    }
    
 //----------------------------------Customer---------------------------------------------------
    @Test
    public void getAccountsById() throws BusinessException {

    	boolean b = appService.getAccountsById(12345);
    	assertEquals(true, b);
    	verify(custMock, times(1)).getAccountsById(12345);
    	
    	boolean b1 = appService.getAccountsById(13);
    	assertEquals(false, b1);
    	verify(custMock, times(1)).getAccountsById(13);
    }
    
    @Test
    public void getTransactionById() throws BusinessException{
    	boolean b = appService.getTransactionById(12345678L);
    	assertEquals(true, b);
    	verify(custMock, times(1)).getTransactionById(12345678L);
    	
    	boolean b1 = appService.getTransactionById(1234L);
    	assertEquals(false, b1);
    	verify(custMock, times(1)).getTransactionById(1234L);
    }
    
    @Test
    public void getTtransfersByAccNumber() throws BusinessException{
    	boolean b = appService.getTtransfersByAccNumber(7564567L);
    	assertEquals(false, b);
    	verify(custMock, times(1)).getTtransfersByAccNumber(7564567L);
    	boolean b1 = appService.getTtransfersByAccNumber(1234567890L);
    	assertEquals(true, b1);
    	verify(custMock, times(1)).getTtransfersByAccNumber(1234567890L);
    }
    
 //----------------------------------Login---------------------------------------------------
    @Test
    public void employeeLogin() throws BusinessException {
    	
    	boolean b = appService.employeeLogin("GG5555", "gala555");
    	assertEquals(true, b);
    	verify(logMock, times(1)).employeeLogin("GG5555", "gala555");
    	
    	boolean b1 = appService.employeeLogin("kasha", "kashamala");
    	assertEquals(false, b1);
    	verify(logMock, times(1)).employeeLogin("kasha", "kashamala");
    }
    
    @Test
    public void letCustomerLogin() throws BusinessException {
    	
    	boolean b = appService.letCustomerLogin("KK", "Klawn");
    	assertEquals(false, b);
    	verify(logMock, times(1)).letCustomerLogin("KK", "Klawn");
    	
       	boolean b1 = appService.letCustomerLogin("KKKK", "Klawn66");
    	assertEquals(true, b1);
    	verify(logMock, times(1)).letCustomerLogin("KKKK", "Klawn66");
    }
    
 //----------------------------------Transaction---------------------------------------------------	
    
    @Test
	public void processTransfer() throws BusinessException {
    	
    	boolean approved = appService.processTransfer(1700, 7213068361L, 1600000L, "approved transfer");
    	assertEquals(true, approved);
    	
    	verify(transMock, times(1)).processTransfer(1700, 7213068361L, 1600000L, "approved transfer");
    }
    
//  @Test
//  public void createLogin() throws BusinessException{
//  	boolean b = appService.createLogin(23454,"LoLo90","CoCo70");
//  	assertEquals(true, b);
//  	verify(custMock, times(1)).createLogin("LoLo90","CoCo70");
//  }
}
