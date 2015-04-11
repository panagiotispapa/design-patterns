package com.design.common;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

public class MappingsTest {

    @Test
    public void testChain() throws Exception {
        Assertions.assertThat(Mappings.<Integer>chain(i -> i * 2, i -> i + 4).apply(3)).isEqualTo(2 * 3 + 4);
    }
}