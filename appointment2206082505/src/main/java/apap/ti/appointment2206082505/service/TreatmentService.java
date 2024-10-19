package apap.ti.appointment2206082505.service;

import java.util.*;

import apap.ti.appointment2206082505.model.Treatment;

public interface TreatmentService {
    Treatment addTreatment(Treatment treatment);
    List<Treatment> getAllTreatment();
}
