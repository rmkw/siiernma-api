CREATE TABLE usuarios.tickets (
    id_ticket BIGSERIAL PRIMARY KEY,
    id_a TEXT NOT NULL,
    id_usuario_reporta BIGINT NOT NULL,
    id_usuario_asignado BIGINT,
    incidencia TEXT NOT NULL,
    propiedad VARCHAR(150),
    estatus VARCHAR(20) NOT NULL DEFAULT 'pendiente',
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_resolucion TIMESTAMP,
    CONSTRAINT fk_ticket_variable FOREIGN KEY (id_a)
        REFERENCES armonizacion.variables (id_a),
    CONSTRAINT fk_ticket_usuario_reporta FOREIGN KEY (id_usuario_reporta)
        REFERENCES usuarios.usuarios (id),
    CONSTRAINT fk_ticket_usuario_asignado FOREIGN KEY (id_usuario_asignado)
        REFERENCES usuarios.usuarios (id),
    CONSTRAINT chk_ticket_estatus CHECK (estatus IN (
        'pendiente', 'en_proceso', 'completado', 'cancelado'
    ))
);
