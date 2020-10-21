package carRent;

import carRent.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    CarRepository carRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaid_Rented(@Payload Paid paid){

        if(paid.isMe()){
            System.out.println("##### listener Rented : " + paid.toJson());


            Optional<Car> carOptional = carRepository.findById(paid.getCarId());

            Car car = carOptional.get();
            car.setCnt(car.getCnt()-paid.getQty());


            carRepository.save(car);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaycanceled_Rentcanceled(@Payload Paycanceled paycanceled){

        if(paycanceled.isMe()){
            System.out.println("##### listener Rentcanceled : " + paycanceled.toJson());

            Optional<Car> carOptional = carRepository.findById(paycanceled.getCarId());

            Car car = carOptional.get();
            car.setCnt(car.getCnt()+paycanceled.getQty());


            carRepository.save(car);
        }
    }

}
