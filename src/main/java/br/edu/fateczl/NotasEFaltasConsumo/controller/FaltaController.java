package br.edu.fateczl.NotasEFaltasConsumo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import br.edu.fateczl.NotasEFaltasConsumo.consumer.FaltasConsumo;
import br.edu.fateczl.NotasEFaltasConsumo.model.Aluno;
import br.edu.fateczl.NotasEFaltasConsumo.model.Disciplina;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/falta")
public class FaltaController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	FaltasConsumo fc = new FaltasConsumo();
	RequestDispatcher rd;
	private String erro = "";
	private String msg = "";
	private List<Aluno> alunos = new ArrayList<>();
	private List<Disciplina> disc = new ArrayList<>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String disciplina = request.getParameter("slIdDisciplina");
		Disciplina d = new Disciplina();
		
		limparAtributos();
		try {
			disc = fc.findDisciplinas();
			d.setCodigo(Long.parseLong(disciplina));
			if(d.getCodigo() != 0) {
				alunos = fc.findAlunos(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
			erro = e.getMessage();
		}
		resposta(request, response);
	}

	private void limparAtributos() {
		erro = "";
		msg = "";
		alunos = new ArrayList<>();		
	}
	
	private void resposta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		rd = request.getRequestDispatcher("index.jsp");
		request.setAttribute("erro", erro);
		request.setAttribute("res", msg);
		request.setAttribute("listaAlunos", alunos);
		request.setAttribute("disciplinas", disc);
		rd.forward(request, response);
	}
}
