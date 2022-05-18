package model;

import java.io.Serializable;
import java.sql.Date;

/**
 * InsuranceOffer entity
 */
public class InsuranceOffer implements Serializable
{
    private static final long serialVersionUID = 1L;

    private long id;
    private double price;
    private Date startDate;
    private Date endDate;
    private Insurer insurer;

    public InsuranceOffer() {}

    /**
     * Constructor for creating new InsuranceOffer object
     * @param id: unique numeric id of a insurance offer
     * @param price: price of insurer's services
     * @param startDate: the beginning of an insurance contract
     * @param endDate: the end of an insurance contract
     * @param insurer: an insurer that provides certain services
     */
    public InsuranceOffer(long id, double price, Date startDate, Date endDate, Insurer insurer)
    {
        super();
        this.id = id;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.insurer = insurer;
    }

    public long getId()
    {
        return id;
    }

    public double getPrice()
    {
        return price;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public Insurer getInsurer()
    {
        return insurer;
    }


    public void setId(long id)
    {
        this.id = id;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public void setInsurer(Insurer insurer)
    {
        this.insurer = insurer;
    }

    @Override
    public String toString()
    {
        return "Insurance offer [id=" + id + ", price=" + price + "]";
    }
}
