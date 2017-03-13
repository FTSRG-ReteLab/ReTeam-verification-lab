package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import sun.management.Sensor;

import static org.mockito.Mockito.*;

public class TrainSensorTest {

    TrainSensor sensor;
    TrainController controller;
    TrainUser user;

    @Before
    public void before() {
        controller = mock(TrainController.class);
        user = mock(TrainUser.class);
        sensor = new TrainSensorImpl(controller, user);
    }

    @Test
    public void SetSpeedLimitToNegative(){
        sensor.overrideSpeedLimit(-50);
        verify(user).setAlarmState(true);
    }

    @Test
    public void SetLowSpeedLimit(){

        when(controller.getReferenceSpeed()).thenReturn(150);

        sensor.overrideSpeedLimit(50);

        verify(user).setAlarmState(true);
    }

    @Test
    public void SetNormalSpeedLimit(){
        sensor.overrideSpeedLimit(100);

        verify(user, times(0)).setAlarmState(true);
    }

    @Test
    public void DoubleAlarm_SetSpeedLimit(){
        sensor.overrideSpeedLimit(-50);
        sensor.overrideSpeedLimit(1000);
        verify(user, times(2)).setAlarmState(true);
    }
}
