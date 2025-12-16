package ps.ins.activos.data.activos.repository

import ps.ins.activos.data.activos.mapper.toDomain
import ps.ins.activos.data.activos.networking.RemoteActivosDataSource
import ps.ins.activos.domain.activos.model.ActivoEntity
import ps.ins.activos.domain.activos.repository.ActivosRepository
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.core.util.map

class ActivosRepositoryImpl(
    private val remoteDataSource: RemoteActivosDataSource
) : ActivosRepository {
    override suspend fun getActivosPropios(): Result<List<ActivoEntity>, NetworkError> {
        return remoteDataSource.getActivosPropios().map { dtos ->
            dtos.map { it.toDomain(isPropio = true) }
        }
    }

    override suspend fun getAllActivos(): Result<List<ActivoEntity>, NetworkError> {
        return remoteDataSource.getAllActivos().map { response ->
            val propios = response.activos_Propios.map { it.toDomain(isPropio = true) }
            val centros = response.centrosCosto?.values?.flatten()?.map { it.toDomain(isPropio = false) } ?: emptyList()
            propios + centros
        }
    }

    override suspend fun getActivoDetail(id: String): Result<ps.ins.activos.domain.activos.model.ActivoDetail, NetworkError> {
         return remoteDataSource.getActivoDetail(id).map { it.toDomain() }
    }

    override suspend fun getHistory(id: String): Result<ps.ins.activos.domain.activos.model.ActivoHistory, NetworkError> {
        return remoteDataSource.getHistory(id).map { dto ->
            ps.ins.activos.domain.activos.model.ActivoHistory(
                idActivo = dto.idActivo,
                nombre = dto.nombre,
                marca = dto.marca,
                modelo = dto.modelo,
                etiqueta = dto.etiqueta,
                numeroSerie = dto.numeroSerie,
                categoria = dto.categoria,
                estado = dto.estado,
                fechaAdquisicion = dto.fechaAdquisicion ?: "N/A",
                imagenUrl = dto.imagenUrl,
                responsableActual = dto.responsableActual ?: "Sin asignar",
                centroCostoActual = dto.centroCostoActual ?: "N/A",
                historial = dto.historialReubicaciones.map { reubicacion ->
                    ps.ins.activos.domain.activos.model.Reubicacion(
                        idReubicacion = reubicacion.idReubicacion,
                        fecha = reubicacion.fecha,
                        motivo = reubicacion.motivo,
                        usuarioAnterior = reubicacion.usuarioAnterior ?: "N/A",
                        centroCostoAnterior = reubicacion.centroCostoAnterior ?: "N/A",
                        usuarioNuevo = reubicacion.usuarioNuevo ?: "N/A",
                        centroCostoNuevo = reubicacion.centroCostoNuevo ?: "N/A"
                    )
                }
            )
        }
    }
}
