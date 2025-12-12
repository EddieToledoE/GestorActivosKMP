package ps.ins.activos.data.activos.mapper

import ps.ins.activos.data.activos.dto.ActivoDetailDto
import ps.ins.activos.domain.activos.model.ActivoDetail

fun ActivoDetailDto.toDomain(): ActivoDetail {
    return ActivoDetail(
        idActivo = idActivo,
        descripcion = descripcion,
        numeroSerie = numeroSerie,
        donacion = donacion,
        facturaPDF = facturaPDF,
        facturaXML = facturaXML,
        cuentaContable = cuentaContable,
        valorAdquisicion = valorAdquisicion,
        fechaAdquisicion = fechaAdquisicion,
        fechaAlta = fechaAlta,
        portaEtiqueta = portaEtiqueta,
        cuentaContableEtiqueta = cuentaContableEtiqueta
    )
}
