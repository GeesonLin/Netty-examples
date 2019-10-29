package com.shensiyuan.thrift;

import com.shensiyuan.netty.sixthexample.MyDataInfo;
import org.apache.thrift.TException;
import thrift.generated.DataException;
import thrift.generated.Person;
import thrift.generated.PersonService;

public class PersonServiceImpl implements PersonService.Iface {

    @Override
    public Person getPersonByUserName(String username) throws DataException, TException {
        System.out.println("Got client receive " + username);

        Person person = new Person();
        person.setUsername(username);
        person.setAge(20);
        person.setMarried(false);

        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("Got client para");

        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
