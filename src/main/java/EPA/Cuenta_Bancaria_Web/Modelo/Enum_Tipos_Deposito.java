package EPA.Cuenta_Bancaria_Web.Modelo;

import java.math.BigDecimal;

public enum Enum_Tipos_Deposito
{
    CAJERO(new BigDecimal("2")),
    SUCURSAL(new BigDecimal("0")),
    OTRA_CUENTA(new BigDecimal("1.5"));

    final BigDecimal costo;

    Enum_Tipos_Deposito(final BigDecimal costo){
        this.costo = costo;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    @Override
    public String toString()
    {
        String sTipo = "Indefinido";

        switch (this)
        {
            case CAJERO: sTipo = "CAJERO"; break;
            case SUCURSAL: sTipo = "SUCURSAL"; break;
            case OTRA_CUENTA: sTipo = "OTRA_CUENTA"; break;
        }

        return sTipo;
    }
}
