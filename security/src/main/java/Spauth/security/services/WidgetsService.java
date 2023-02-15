package Spauth.security.services;

import java.util.List;

import Spauth.security.model.Widgets;
import Spauth.security.repository.WidgetsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WidgetsService {
    @Autowired
    WidgetsRepository widgetsRepository;

    public Iterable<Widgets> getWidgets(Long userId) {

        return widgetsRepository.getByUserId(userId);
    }

    public List<Widgets> getByValue(String value) {
        List<Widgets> widget = widgetsRepository.findAllByValue(value);
        return widget;
    }

    public void delete(Widgets widget) {
        widgetsRepository.delete(widget);
    }

    public void deleteByValue(String value) {
        widgetsRepository.deleteByValue(value);
    }

}