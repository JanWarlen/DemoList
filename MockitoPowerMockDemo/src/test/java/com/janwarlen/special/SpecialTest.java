package com.janwarlen.special;

import com.janwarlen.BasedTest;
import com.janwarlen.Special.Special;
import com.janwarlen.model.Person;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @ClassName: SpecialTest
 * @author: janwarlen
 * @Date: 2019/2/12 14:07
 * @Description:
 */
@PrepareForTest({Special.class})
public class SpecialTest extends BasedTest {

    @Test
    public void testMockNew() throws Exception {
        Person mockito = new Person("mockito", 18);
        // 拦截对象创建，狸猫换太子
        PowerMockito.whenNew(Person.class)
                .withArguments("spcial", 16).thenReturn(mockito);
        Person newPerson = Special.createNewPerson();
        System.out.println(newPerson.sayHi("test"));
    }


}
