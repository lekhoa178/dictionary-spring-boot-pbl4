package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.DataPerDayRepository;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.entity.embeddable.DataPerDayId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class DataPerDayServiceImpl implements DataPerDayService {

    private final DataPerDayRepository dataPerDayRepository;

    @Autowired
    public DataPerDayServiceImpl(DataPerDayRepository dataPerDayRepository) {
        this.dataPerDayRepository = dataPerDayRepository;
    }

    public List<DataPerDay> getAccountDPDs(Integer accountId) {
        return dataPerDayRepository.findAllByIdAccountId(accountId);
    }

    public DataPerDay getAccountDPD(Integer accountId) {
        Optional<DataPerDay> result = dataPerDayRepository.findById(new DataPerDayId(getDayId(), accountId));

        return result.orElse(null);
    }

    public DataPerDay updateAccountDPD(Integer accountId, int exp, int onlHours) {
        DataPerDay dpd = getAccountDPD(accountId);
        dpd.setExperience(dpd.getExperience() + exp);
        dpd.setOnlineHours(dpd.getOnlineHours() + onlHours);

        return dataPerDayRepository.save(dpd);
    }

    public Integer getDayId() {
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Integer.valueOf(currentDate.format(formatter));
    }

}
