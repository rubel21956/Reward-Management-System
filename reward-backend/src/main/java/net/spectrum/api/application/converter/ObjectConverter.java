package net.spectrum.api.application.converter;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;

public class ObjectConverter <Source, Target extends Class> {
    
    ModelMapper modelMapper = new ModelMapper();

    public Target convert(Source source, Target target) {
        return modelMapper.map(source , (Type) target.getClass());
    }
}
