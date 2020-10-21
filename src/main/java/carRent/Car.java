package carRent;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Car_table")
public class Car {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status;
    private Integer cnt;

    @PostPersist
    public void onPostPersist(){
        CarRegistered carRegistered = new CarRegistered();
        BeanUtils.copyProperties(this, carRegistered);
        carRegistered.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){
        CarRented carRented = new CarRented();
        BeanUtils.copyProperties(this, carRented);
        carRented.publishAfterCommit();


        CarRentCanceled carRentCanceled = new CarRentCanceled();
        BeanUtils.copyProperties(this, carRentCanceled);
        carRentCanceled.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }




}
