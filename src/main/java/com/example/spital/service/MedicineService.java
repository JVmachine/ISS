package com.example.spital.service;

import com.example.spital.model.Medicine;
import com.example.spital.repository.IMedicineRepository;
import com.example.spital.repository.MedicineRepository;
import com.example.spital.utils.Observable;
import com.example.spital.utils.Observer;
import com.example.spital.utils.events.ChangeEventType;
import com.example.spital.utils.events.MedicineEvent;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MedicineService implements Observable<MedicineEvent> {
    private IMedicineRepository medicineRepository;
    private List<Observer<MedicineEvent>> observerList = new ArrayList<>();

    public MedicineService(IMedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Iterable<Medicine> getAll(){
        return medicineRepository.findAll();
    }

    public void saveMedicine(String medicineName, Date medicineExpirationDate, Integer medicineQuantity) {
        Medicine medicine = new Medicine(medicineName,medicineExpirationDate,medicineQuantity);
        medicineRepository.save(medicine);
        notifyObservers(new MedicineEvent(ChangeEventType.ADD,medicine));
    }
    public Medicine getMedicineByName(String name){
        return medicineRepository.getMedicineByName(name);
    }

    @Override
    public void addObserver(Observer<MedicineEvent> e) {
        observerList.add(e);
    }

    @Override
    public void removeObserver(Observer<MedicineEvent> e) {
        observerList.remove(e);
    }

    @Override
    public void notifyObservers(MedicineEvent t) {
        observerList.forEach(x -> x.update(t));
    }

    public void update(Integer idMedicine,String medicineName, Date medicineExpirationDate, Integer medicineQuantity) {
        Medicine newMedicine = new Medicine(medicineName,medicineExpirationDate,medicineQuantity);
        medicineRepository.update(newMedicine,idMedicine);
        notifyObservers(new MedicineEvent(ChangeEventType.UPDATE,newMedicine));
    }

    public void updateQuantity(Integer idMedicine,Integer oldMedicineQuantity,Integer newMedicineQuantity) {
        Medicine medicine = medicineRepository.findOne(idMedicine);
        int finalQuantity = oldMedicineQuantity - newMedicineQuantity;
        medicine.setQuantity(finalQuantity);
        medicineRepository.update(medicine,idMedicine);
        notifyObservers(new MedicineEvent(ChangeEventType.UPDATE_QUANTITY,medicine));
    }

    public void delete(Medicine medicine) {
        medicineRepository.delete(medicine.getId());
        notifyObservers(new MedicineEvent(ChangeEventType.DELETE,medicine));
    }
}
