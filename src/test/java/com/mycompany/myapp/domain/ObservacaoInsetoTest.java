package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ObservacaoInsetoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservacaoInseto.class);
        ObservacaoInseto observacaoInseto1 = new ObservacaoInseto();
        observacaoInseto1.setId(1L);
        ObservacaoInseto observacaoInseto2 = new ObservacaoInseto();
        observacaoInseto2.setId(observacaoInseto1.getId());
        assertThat(observacaoInseto1).isEqualTo(observacaoInseto2);
        observacaoInseto2.setId(2L);
        assertThat(observacaoInseto1).isNotEqualTo(observacaoInseto2);
        observacaoInseto1.setId(null);
        assertThat(observacaoInseto1).isNotEqualTo(observacaoInseto2);
    }
}
