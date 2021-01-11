package com.sverbank.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Customer;
import com.sverbank.service.AppService;

public class AppMockitoTest {
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
    @InjectMocks  
    AppService appService;
    
    @Mock      
    CustomerDAOImpl dbMock; 
    
    @Test
	public void getCustomerBySSN() throws BusinessException {
    	
    	boolean got = appService.getCustomerBySSN(884645367L);
    	assertEquals(true, got);
    	
        verify(dbMock, times(1)).getCustomerBySSN(884645367L); 
		
	}
    
    @Test
	public void approveTransfer() throws BusinessException {
    	
    	boolean approved = appService.approveTransfer(1700, 7213068361L, 16);
    	assertEquals(true, approved);
    	
    	verify(dbMock, times(1)).approveTransfer(1700, 7213068361L, 16);
    }
}
