package br.edu.fateczl.NotasEFaltasConsumo.consumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.edu.fateczl.NotasEFaltasConsumo.model.Aluno;
import br.edu.fateczl.NotasEFaltasConsumo.model.Disciplina;
import br.edu.fateczl.NotasEFaltasConsumo.model.Falta;

public class FaltasConsumo {
	private final String URL = "http://localhost:8080/NotasEFaltas/api/";
	
	private String getAlunosDisc(Disciplina d) throws Exception{
		try {
			String urlFinal = URL+"alunodisciplina/"+d.getCodigo();
			return getJson(urlFinal);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getDisc() throws Exception{
		try {
			String urlFinal = URL+"disciplina/";
			return getJson(urlFinal);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Disciplina> findDisciplinas() throws Exception {
		String json = getDisc();
		List<Disciplina> l = convertJsonToDisciplinaList(json);
		return l;
	}
	
	private List<Disciplina> convertJsonToDisciplinaList(String json) {
		Gson gson = new Gson();
		List<Disciplina> d = new ArrayList<>();
		
		TypeToken<List<Disciplina>> token = new TypeToken<List<Disciplina>>(){};
		Type tipo = token.getType();
		
		d = gson.fromJson(json, tipo);
		return d;
	}

	public List<Aluno> findAlunos(Disciplina d) throws Exception{
		String json = getAlunosDisc(d);
		List<Aluno> l = convertJsonToAlunoList(json);
		return l;
	}
	private List<Aluno> convertJsonToAlunoList(String json) {
		Gson gson = new Gson();
		List<Aluno> a = new ArrayList<>();
		
		TypeToken<List<Aluno>> token = new TypeToken<List<Aluno>>(){};
		Type tipo = token.getType();
		
		a = gson.fromJson(json, tipo);
		return a;
	}

	public String send(Falta f,String method) throws Exception {
		URL u = new URL(URL+"falta");
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setConnectTimeout(20000);
		conn.setReadTimeout(20000);
		conn.setDoOutput(true);
		conn.setUseCaches(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("accept", "text/plain");
		
		OutputStream out = conn.getOutputStream();
		String json = convertToJson(f);
		byte[] bytes = json.getBytes("UTF-8");
		out.write(bytes);
		out.flush();
		out.close();
		
		InputStream in = conn.getInputStream();
		byte[] bytesResponse = new byte[2048];
		StringBuffer sbRes = new StringBuffer();
		int i = 0;
		while((i = in.read(bytesResponse)) != -1) {
			sbRes.append(new String(bytesResponse,0,i));
		}
		in.close();
		
		return sbRes.toString();
	}
	
	private String convertToJson(Falta f) {
		Gson gson = new Gson();
		String json = gson.toJson(f);
		return json;
	}

	private String getJson(String urlFinal) throws Exception{
		Charset c = Charset.forName("UTF-8");
		URL u = new URL(urlFinal);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		if(conn.getResponseCode()!=200) {
			throw new IOException("Erro : "+conn.getResponseCode());
		}
		
		InputStream is = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is,c);
		BufferedReader br = new BufferedReader(isr);
		String saida = br.readLine();
		StringBuffer sb = new StringBuffer();
		
		while(saida != null) {
			sb.append(saida);
			saida = br.readLine();
		}
		br.close();
		isr.close();
		
		return sb.toString();
	}
}
