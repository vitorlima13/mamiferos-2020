package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LojaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Loja.class);
        Loja loja1 = new Loja();
        loja1.setId(1L);
        Loja loja2 = new Loja();
        loja2.setId(loja1.getId());
        assertThat(loja1).isEqualTo(loja2);
        loja2.setId(2L);
        assertThat(loja1).isNotEqualTo(loja2);
        loja1.setId(null);
        assertThat(loja1).isNotEqualTo(loja2);
    }
}
