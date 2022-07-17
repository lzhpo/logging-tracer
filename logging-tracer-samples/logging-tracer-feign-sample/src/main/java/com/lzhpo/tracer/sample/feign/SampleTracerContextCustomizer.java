package com.lzhpo.tracer.sample.feign;

import com.lzhpo.tracer.TracerContextCustomizer;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * @author lzhpo
 */
@Component
public class SampleTracerContextCustomizer implements TracerContextCustomizer {

  @Override
  public void customize(Map<String, String> context) {
    context.put("abc", "123");
    context.put("def", "456");
  }
}
