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
        sensor.overrideSpeedLimit(200);
        user.overrideJoystickPosition(150);
        verify(user).overrideJoystickPosition(150);

        when(controller.getReferenceSpeed()).thenReturn(150);
        controller.getReferenceSpeed();

        sensor.overrideSpeedLimit(50);

        verify(user).setAlarmState(true);
    }

    @Test
    public void SetSpeedLimit(){
        sensor.overrideSpeedLimit(100);
        when(user.getAlarmState()).thenReturn(false) ;
        boolean state = user.getAlarmState();

        Assert.assertEquals(false, state);
    }

    @Test
    public void SetNormalSpeedLimit(){
        sensor.overrideSpeedLimit(-50);
        sensor.overrideSpeedLimit(1000);
        verify(user, times(2)).setAlarmState(true);
    }
}
