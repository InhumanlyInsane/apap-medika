package apap.ti.appointment2206082505;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import apap.ti.appointment2206082505.model.Appointment;
import apap.ti.appointment2206082505.repository.AppointmentDb;
import apap.ti.appointment2206082505.service.AppointmentService;

import java.util.Date;
import java.util.Calendar;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "DATABASE_URL_DEV=jdbc:h2:mem:testdb",
    "DEV_USERNAME=sa",
    "DEV_PASSWORD="
})
class Appointment2206082505ApplicationTests {

    @Autowired
    private AppointmentService appointmentService;

    @MockBean
    private AppointmentDb appointmentDb;

    @Test
    void contextLoads() {
        // This test ensures that the Spring context loads correctly
    }

    @Test
    void testGetAppointmentsByDateRange() {
        // Arrange
        Calendar cal = Calendar.getInstance();
        
        cal.set(2024, Calendar.JANUARY, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startDate = cal.getTime();

        cal.set(2024, Calendar.JANUARY, 31, 23, 59, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date endDate = cal.getTime();

        Appointment appointment1 = new Appointment();
        cal.set(2024, Calendar.JANUARY, 15, 10, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        appointment1.setDate(cal.getTime());

        Appointment appointment2 = new Appointment();
        cal.set(2024, Calendar.JANUARY, 20, 14, 30, 0);
        cal.set(Calendar.MILLISECOND, 0);
        appointment2.setDate(cal.getTime());

        List<Appointment> expectedAppointments = Arrays.asList(appointment1, appointment2);

        when(appointmentDb.findByDateBetween(startDate, endDate))
            .thenReturn(expectedAppointments);

        // Act
        List<Appointment> actualAppointments = appointmentService.getAppointmentsByDateRange(startDate, endDate);

        // Assert
        assertEquals(expectedAppointments.size(), actualAppointments.size());
        assertTrue(actualAppointments.containsAll(expectedAppointments));
        verify(appointmentDb).findByDateBetween(startDate, endDate);
    }
}