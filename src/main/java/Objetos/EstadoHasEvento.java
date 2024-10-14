package Objetos;

import java.util.Date;

public class EstadoHasEvento {

    private int id;
    private Estado estado;
    private Evento evento;
    private Date dataHoraInicio;
    private Date dataHoraTermino;

    public EstadoHasEvento(){}

    public EstadoHasEvento(int id, Estado estado, Evento evento, Date dataHoraInicio, Date dataHoraTermino) {
        this.id = id;
        this.estado = estado;
        this.evento = evento;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraTermino = dataHoraTermino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Date dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Date getDataHoraTermino() {
        return dataHoraTermino;
    }

    public void setDataHoraTermino(Date dataHoraTermino) {
        this.dataHoraTermino = dataHoraTermino;
    }
}
