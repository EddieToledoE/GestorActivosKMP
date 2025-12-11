package ps.ins.activos.data.activos.mapper

import ps.ins.activos.data.activos.dto.ActivoDto
import ps.ins.activos.domain.activos.model.ActivoEntity

fun ActivoDto.toDomain(isPropio: Boolean = false): ActivoEntity {
    return ActivoEntity(
        idActivo = idActivo,
        nombre = nombre,
        categoria = categoria,
        responsable = responsable,
        centroCosto = centroCosto,
        estado = estado,
        fechaAdquisicion = fechaAdquisicion,
        imagenUrl = imagenUrl,
        marca = marca,
        modelo = modelo,
        etiqueta = etiqueta,
        isPropio = isPropio
    )
}
