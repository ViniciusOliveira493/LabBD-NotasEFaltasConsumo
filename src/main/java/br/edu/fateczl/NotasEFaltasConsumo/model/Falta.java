package br.edu.fateczl.NotasEFaltasConsumo.model;

import java.time.LocalDate;

public class Falta{
	private Long RA;
	private Long codigo;
	private LocalDate data;
	private Integer presenca;
	
	public Long getRA() {
		return RA;
	}
	public void setRA(long rA) {
		RA = rA;
	}
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public int getPresenca() {
		return presenca;
	}
	public void setPresenca(int presenca) {
		this.presenca = presenca;
	}
	
}
