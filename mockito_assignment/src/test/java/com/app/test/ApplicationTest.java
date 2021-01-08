package com.app.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



//First way
//import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;

//Second way
//import org.junit.Rule;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//import org.mockito.quality.Strictness;

//Third way
import org.junit.Before;
import org.mockito.MockitoAnnotations;


import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.app.DAO.DatabaseDAO;
import com.app.DAO.NetworkDAO;
import com.app.service.RecordService;



//First way                                                // Annotate the test so that mockito can process the annotations.
// @RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {
	
//Second way   
//@Rule public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);
	
// Third way
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	 
	
	
    @InjectMocks  
    RecordService recordService;   //Annotate service field with the @InjectMocks annotation to first instantiate and then inject both mocked dependencies.
    
   
    @Mock      
    DatabaseDAO databaseMock;   //Annotate the dao fields with the @Mock annotation to have a mock object instantiated for both of them.
     
    @Mock
    NetworkDAO networkMock;
    
    @Test
    public void saveTest()
    {
        boolean saved = recordService.save("temp.txt");  //Call the method to test on the class to be tested ie. recordService.
        assertEquals(true, saved);
         
        verify(databaseMock, times(1)).save("temp.txt");  //Verify that methods in the mocked objects have been invoked.
        verify(networkMock, times(1)).save("temp.txt");
    }
}
