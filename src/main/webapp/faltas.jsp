<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Chamada e Faltas</title>
	</head>
	<body>
	<jsp:include page="./menu.jsp" />
		<main class="conteudo">
			<div>
				<Select id="slIdDisciplina" name="slIdDisciplina">
					<option value="0">Escolha uma Disciplina</option>
				</Select> 
				<input type="button" id="btnBuscaralunos" value="Buscar Alunos">
				<input type="button" id="btnGerarRelatorio" value="Gerar relatório">
			</div>
			<div>	
				Data da Aula:	
				<input type="date" id="txtData">
			</div>
			<div id="table">		
				
			</div>
			<div>
				<input type="button" id="btnChamada" value="Fazer Chamada">
			</div>	
		</main>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>	
		<script type="text/javascript">
			document.onload = carregarDisciplinas();
			var URL = 'http://localhost:8080/api/'
			function carregarDisciplinas(){
				$.ajax({
					url : 'http://localhost:8080/api/'+'disciplina',
					contentType: "application/json",
					type : "GET",
					data : ""
				})
				.done(function(msg){
			        let select = document.getElementById('slIdDisciplina');
			        msg.forEach(e => {
			            select.append(criarOption(e));
			        });
					
				})
				.fail(function(jqXHR, textStatus, msg){
					console.log(msg);
				});
			}
			function criarOption(opcao){
			    return new Option(opcao.nome+' '+opcao.codigo,opcao.codigo)
			}
			
			function criarTrAluno(aluno){
				var tr = document.createElement("tr");
				var tdNome = document.createElement("td");
				var tdRa= document.createElement("td");
				var tdInput = document.createElement("td");
				
				tdNome.append(aluno.nome);
				tdRa.append(aluno.ra);
				var input = document.createElement("INPUT");
				input.setAttribute("type", "number");
				input.setAttribute("id", "faltas");
				input.setAttribute("min", "0");
				input.setAttribute("max", "4");
				input.setAttribute("value", "0");
				tdInput.append(input);
				
				tr.append(tdRa);
				tr.append(tdNome);
				tr.append(tdInput);
			    return tr
			}
			var btnBuscar = document.getElementById('btnBuscaralunos');
			btnBuscar.addEventListener("click", function(){
				let select = document.getElementById('slIdDisciplina');
				var id = select.value;
				
				$.ajax({
					url : 'http://localhost:8080/api/'+'alunodisciplina/'+id,
					contentType: "application/json",
					type : "GET",
					data : ""
				})
				.done(function(msg){
			        let div = document.getElementById('table');
			        div.innerText="";
			        var tabela = document.createElement("table");
			        var cabecalho = document.createElement("thead");
			        var corpo = document.createElement("tbody")
			        tabela.setAttribute("id", "tbAlunos"); 
			        
			        var headNome = document.createElement("th");
			        var headRa = document.createElement("th");
			        var headPresenca = document.createElement("th");
			        
			        headNome.append("Nome");
			        headRa.append("RA");
			        headPresenca.append("Faltas");
			        
			        cabecalho.appendChild(headRa);
			        cabecalho.appendChild(headNome);
			        cabecalho.appendChild(headPresenca);
			        
			        tabela.appendChild(cabecalho);
			        msg.forEach(e => {
			            corpo.append(criarTrAluno(e));
			        });
			        tabela.appendChild(corpo);
			        div.append(tabela)
					
				})
				.fail(function(jqXHR, textStatus, msg){
					console.log(msg);
				});
				
			});
			
			var btnFazerChamada = document.getElementById('btnChamada');
			btnFazerChamada.addEventListener("click", function(){
				var table = document.getElementById("tbAlunos");
				var select = document.getElementById('slIdDisciplina');
				var iddisc = select.value;
				
				var txtData = document.getElementById('txtData');
				var data = txtData.value;
				
				if(data.length !== 0)
					{
					for (var i = 0, row; row = table.rows[i]; i++) {
						table.rows[i]
						var inputv = table.rows[i].cells[2].querySelectorAll("input");
						
						var falta = new Object();
						falta.ra = table.rows[i].cells[0].innerHTML.trim()
						falta.codigo = iddisc;
						falta.data = data;
						falta.presenca = inputv[0].value
						
						var faltaAluno = JSON.stringify(falta);
						
						var mensagem = ""
						$.ajax({
							url : 'http://localhost:8080/api/'+'falta',
							contentType: "application/json",
							type : "POST",
							data : faltaAluno
						})
						.done(function(msg){
					        mensagem = msg
						})
						.fail(function(jqXHR, textStatus, msg){
							console.log(msg);
							mensagem = msg
						});
					}
					alert("Chamada realizada com sucesso");
					location.reload();
				}else{
					alert("Selecione a data");
				}
				
			});
		</script>
	</body>
</html>