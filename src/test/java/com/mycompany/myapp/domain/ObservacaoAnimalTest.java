package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ObservacaoAnimalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservacaoAnimal.class);
        ObservacaoAnimal observacaoAnimal1 = new ObservacaoAnimal();
        observacaoAnimal1.setId(1L);
        ObservacaoAnimal observacaoAnimal2 = new ObservacaoAnimal();
        observacaoAnimal2.setId(observacaoAnimal1.getId());
        assertThat(observacaoAnimal1).isEqualTo(observacaoAnimal2);
        observacaoAnimal2.setId(2L);
        assertThat(observacaoAnimal1).isNotEqualTo(observacaoAnimal2);
        observacaoAnimal1.setId(null);
        assertThat(observacaoAnimal1).isNotEqualTo(observacaoAnimal2);
    }
}
