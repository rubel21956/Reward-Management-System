package net.spectrum.api.util.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Converter<source, destination> {

    ModelMapper modelMapper = new ModelMapper();

    public  destination convert(source source, destination destination){
        return (destination) modelMapper.map(source, destination.getClass());
    }
}
