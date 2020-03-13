package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class InsetoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inseto.class);
        Inseto inseto1 = new Inseto();
        inseto1.setId(1L);
        Inseto inseto2 = new Inseto();
        inseto2.setId(inseto1.getId());
        assertThat(inseto1).isEqualTo(inseto2);
        inseto2.setId(2L);
        assertThat(inseto1).isNotEqualTo(inseto2);
        inseto1.setId(null);
        assertThat(inseto1).isNotEqualTo(inseto2);
    }
}
