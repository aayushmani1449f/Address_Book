package com.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseCountTest {
    private DatabaseDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new DatabaseDataSource();
    }

    @Test
    void testCountByCity() {
        
        
        int count = dataSource.getContactCountByCity("NewCityName");
        
        
        
        assertTrue(count >= 0, "Count by city should execute successfully");
    }

    @Test
    void testCountByState() {
        
        int count = dataSource.getContactCountByState("IL");
        assertTrue(count >= 0, "Count by state should execute successfully");
    }
}
