package model;

import java.io.Serializable;

/**
 * Insurer entity
 */
public class Insurer implements Serializable
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    public Insurer() {}

    /**
     * Constructor for creating new Insurer object
     * @param id: unique numeric id of an insurer
     * @param name: name of the insurer
     * @param email: email of the insurer
     * @param phoneNumber: phone number of the insurer
     * @param address: a physical address of the insurer
     */
    public Insurer(long id, String name, String email, String phoneNumber, String address)
    {
        super();
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
    @Override
    public String toString()
    {
        return "Insurer [name=" + name + ", phone number=" + phoneNumber + "]";
    }
}
