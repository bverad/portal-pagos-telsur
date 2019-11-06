﻿// JavaScriptDocument
function agregaPuntosFijo(val){
	
	//return val;
	var valor = new String(val);
	var largo = valor.length;	
	var retorno = '';
	var cont = 0;
	if(largo > 3){	
		for(i=largo-1; i >= 0; i--){
			if(cont == 3){
				retorno = '.'+retorno
				cont = 0
			}
			retorno = valor.charAt(i)+retorno
			cont++;
		}
		return retorno;
	}
	else{
		return val;	
	}
}

function format(input)
{
	var num = input.replace(/\./g,'');
	if(!isNaN(num))
	{
	   num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g,'$1.');
	   num = num.split('').reverse().join('').replace(/^[\.]/,'');
	   return num;
	}
	else
	{ 
	   //alert("Solo se permiten numeros");
	   input = input.replace(/[^\d\.]*/g,'');
	   return input;
	}
}



function puntitos(donde,caracter){

	pat = /[\*,\+,\(,\),\?,\,$,\[,\],\^]/
	valor = donde.value
	largo = valor.length
	crtr = true
	if(isNaN(caracter) || pat.test(caracter) == true){
		if (pat.test(caracter)== true ){ 
			caracter = "\ " + caracter
		}
		carcter = new RegExp(caracter,"g")
		valor = valor.replace(carcter,"")
		donde.value = valor
		crtr = false
	}
	else{
		var nums = new Array()
		cont = 0
		for(m=0;m<largo;m++){
			if(valor.charAt(m) == "." || valor.charAt(m) == " ")
				{continue;}
			else{
				nums[cont] = valor.charAt(m)
				cont++
			}
		}
	}
	var cad1="",cad2="",tres=0
	if(largo > 3 && crtr == true){
		for (k=nums.length-1;k>=0;k--){
			cad1 = nums[k]
			cad2 = cad1 + cad2
			tres++
			if((tres%3) == 0){
				if(k!=0){
					cad2 = "." + cad2
				}
			}
		}
		donde.value = cad2
	}
}
	
function IsNumber(e) {
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla==8 || tecla==0) return true;
	patron = /\d/; // Solo acepta numeros
	te = String.fromCharCode(tecla);
	return patron.test(te);
}
function IsNumberDec(e) {
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla==8 || tecla==0) return true;
	patron = /[.,0123456789]/; // Solo acepta numeros
	te = String.fromCharCode(tecla);
	return patron.test(te);
}
function IsRut(e){
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla==8 || tecla ==0) return true;
	patron = /[-kK0123456789\s-]/;
	te = String.fromCharCode(tecla);
	return patron.test(te)
} 

function IsFijo(e){
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla==8 || tecla ==0) return true;
	patron = /[-0123456789\s\/-]/;
	te = String.fromCharCode(tecla);
	return patron.test(te)
} 

function IsCelular(e){
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla==8 || tecla ==0) return true;
	patron = /[-0123456789\s-]/;
	te = String.fromCharCode(tecla);
	return patron.test(te)
} 

function getRadioButtonSelectedValue(ctrl) {
	for(i=0;i<ctrl.length;i++)
		if(ctrl[i].checked) return ctrl[i].value;
}
function IsNombre(e){
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla==8 || tecla==0) return true;
	patron = /[a-zA-ZáéíóúñÁÉÍÓÚÑ\s-]/;
	te = String.fromCharCode(tecla);
	return patron.test(te); 
}

function IsTexto(e){
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla==8 || tecla==0) return true;
	patron = /[a-zA-Z0-9_.,:;?¿!¡@áéíóúñÁÉÍÓÚÑ\s-]/;
	te = String.fromCharCode(tecla);
	return patron.test(te); 
}

function IsIdServicio(e){
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla==8 || tecla==0) return true;
	patron = /[a-zA-Z0-9\s-]/;
	te = String.fromCharCode(tecla);
	return patron.test(te); 
}

function IsCasilla(e){
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla==8 || tecla==0) return true;
	patron = /[a-zA-Z0-9_.@\s-]/;
	te = String.fromCharCode(tecla);
	return patron.test(te); 
}

function validarEntero(valor){ 
if (valor == ''){
	valor = true
}else{
	valor = isNaN(valor)
}
return valor
}

function isEmailAddress(stremail){
	var s = stremail
	var filter=/^[A-Za-z][A-Za-z0-9_.]*@[A-Za-z0-9_]+\.[A-Za-z0-9_.]+[A-za-z]$/;
	if (s.length == 0 ) return false;
	if (filter.test(s)){
		return true;
	}else {
		return false;
	}
}

function ltrim(s) {
	return s.replace(/^\s+/, "");
}

function rtrim(s) {
	return s.replace(/\s+$/, "");
}

function trim(s) {
	return rtrim(ltrim(s)); 
}

function radioValor(ctrl) {
	for(i=0;i<ctrl.length;i++)
		if(ctrl[i].checked) return ctrl[i].value;
}

function paginador(pag,totalPag){
	for(var i=1; i <= totalPag; i++){
		jQuery('#pag-'+i).hide();	
	}
	jQuery('#pag-'+pag).fadeIn('slow');
}

function ValidaRut( Objeto )
{	
	var rut = "";
	var tmpstr = "";
	var intlargo = Objeto;
	if (intlargo.length> 0)
	{
		crut = Objeto;
		largo = crut.length;
		if ( largo <2 )
		{	
			console.log('Rut inválido');
			return false;
		}
		for ( i=0; i <crut.length ; i++ )
		if ( crut.charAt(i) != ' ' && crut.charAt(i) != '.' && crut.charAt(i) != '-' )
		{
			tmpstr = tmpstr + crut.charAt(i);
		}
		rut = tmpstr;
		crut=tmpstr;
		largo = crut.length;
	
		if ( largo> 2 )
			rut = crut.substring(0, largo - 1);
		else
			rut = crut.charAt(0);
	
		dv = crut.charAt(largo-1);
	
		if ( rut == null || dv == null )
		return 0;
	
		var dvr = '0';
		suma = 0;
		mul  = 2;
	
		for (i= rut.length-1 ; i>= 0; i--)
		{
			suma = suma + rut.charAt(i) * mul;
			if (mul == 7)
				mul = 2;
			else
				mul++;
		}
	
		res = suma % 11;
		if (res==1)
			dvr = 'k';
		else if (res==0)
			dvr = '0';
		else
		{
			dvi = 11-res;
			dvr = dvi + "";
		}
	
		if ( dvr != dv.toLowerCase() )
		{
			//alert('El Rut Ingreso es Invalido');
			return false;
		}
		return true;
	}
}

$(document).on('submit', 'form', function() {
	
	var sw = true;
	var $form = $(this),
		$button,
		label;
	$form.find(':submit').each(function() {
		$button = $(this);
		label = $button.data('after-submit-value');
		if (typeof label != 'undefined') {
			$button.val(label).prop('disabled', true);
			if(jQuery('#tipo').val()==''){
				sw='seltipo';
				$button.val("Consultar").prop('disabled', false);
				alert('Seleccione un tipo de busqueda');
				sw = false;
				return false;
			}
			if(jQuery('#selZona').val()==''){
				sw='selZona';
				$button.val("Consultar").prop('disabled', false);
				alert('Seleccione una empresa');
				sw = false;
				return false;
			}
			if (jQuery('#tipo').val()=="rut") {
				if(jQuery('#txtRut').val()==''){
					sw='txtRut';
					$button.val("Consultar").prop('disabled', false);
					alert('Ingrese un rut');
					sw = false;
					return false;
				}else if(ValidaRut(jQuery('#txtRut').val())==false){
					$button.val("Consultar").prop('disabled', false);
					alert('Ingrese un rut válido');
					sw = false;
					return false;
				}
			}
		}
	});
	
	//si existen errores limpia y se situa en el componente original
	if(sw === false){
		$("#txtRut").focus();
		$("#txtRut").val('');

	}
	
	return sw;
	
//	$(".btn-telsur").attr("disabled", true);
//	var loadingText = '<i class="fa fa-circle-o-notch fa-spin"></i> Consultando...'; 
//	if ($(".btn-telsur").html() !== loadingText) {
//		$(".btn-telsur").data('original-text', $(".btn-telsur").html());
//		$(".btn-telsur").html(loadingText); 
//	  /*setTimeout(function() {
//	  $this.html($this.data('original-text'));
//	    }, 2000);*/
//	}
	
	

	
	//$('#formLogin').submit();
	
	
});


function ValidaIngreso(){
		
	sw='';
	if(jQuery('#tipo').val()==''){
		sw='seltipo';
		alert('Seleccione un tipo de busqueda');
		return false
	}
	if(jQuery('#selZona').val()==''){
		sw='selZona';
		alert('Seleccione una empresa');
		return false
	}
	if (jQuery('#tipo').val()=="rut") {
		if(jQuery('#txtRut').val()==''){
			sw='txtRut';
			alert('Ingrese un rut');
			return false;
		}else if(ValidaRut(jQuery('#txtRut').val())==false){
			alert('Ingrese un rut válido');
			return false;
		}
	}
	
	$('#formLogin').submit();
	
	/*if (jQuery('#tipo').val()=="servicio") {
		/*if(jQuery('#selArea').val()==''){
			sw='selArea';
			alert('Seleccione zona');
			return false
		}	

		alert(jQuery('#selservicio').val());
		if(jQuery('#selservicio').val()==''){
			sw='servicio';
			alert('ingrese numero de servicio');
			return false
		}
	}*/
	
	//alert(sw);
	/*if(trim(sw)==''){
		if (jQuery('#tipo').val()=="rut"){
			//var action="pago-de-cuentas/empresa/"
			var action="/empresa/"+jQuery('#txtRut').val();
			console.debug(action);
		}else{
			var action ="/seleccionar-documentos-servicio/"
		}
		jQuery('#formLogin').attr("action",action);
		jQuery('#formLogin').attr("method",'GET')
		jQuery('#formLogin').submit();
	}else{
		return false;
	}*/
	
	//enviando contenido del formulario
	
	//var rut = jQuery('#txtRut').val();
	//window.location.href = window.location.origin + "/pagos/lista_deudas/"+ rut;
	/*var search = {}
    search["rut"] = rut;*/
	/*$.ajax({
		type : 'POST',
		contentType: "application/json",
		url : '/pagos-telsur/api/pagos/verificar_mp/' + rut,
		//data: JSON.stringify(search),
		dataType: 'json',
	    cache: false,
	    timeout: 600000,
		success : function(data) {
			if(data.mensaje == "0"){
				window.location.href = window.location.origin + "/pagos-telsur/pagos/lista_deudas/" + rut;
			}else{
				//no existen datos para el rut consultado por lo tanto el mensaje posee contenido.
				$("#alert-cdeuda").show();
				$("#alert-cdeuda").html("<div class='alert alert-danger' role='alert'><span>" + data.mensaje + "</span></div>");
				$("#alert-cdeuda").delay(3000).fadeOut(500);
			}
				
		},
        error: function (e) {
        	console.log("ERROR : ", e);
			$("#alert-cdeuda").html("<div class='alert alert-danger' role='alert'><span>Error al invocar servicio de consulta de pagos</span></div>");
        }
	});*/

	//window.location.href = BASE_URL + "empresa/"+rut;
	
}


function getAll()
{

	var chklen;
	var chkchkd = 0;
	var ok ='si';
	var allParam = document.getElementsByTagName("input"); //allParam recive a todos los Elementos del tipo 'input'
	var chkbcs = new Array() //chkbcs = Array de todos los Checkboxs
	var j = 0; //J = contador de posiciones del Array
	//Desde 0 hasta que el Largo de allParam sea menor a la posicion de 'i'
	for(i=0;i<allParam.length;i++){
		var input = allParam[i]; //input = allParam del tipo 'input'
		//Si el 'input' es del Tipo ='checbox'
		if(input.type=="checkbox" && input.name=="valor"){
			chkbcs[j] = input; //Almacena si es un 'checbox'
			j++; //Aumento el contador
			if(input.checked)//Si el input esta 'checked'
			chkchkd++; //Aumento el contador
		}
	}

	chklen = chkbcs.length; //Array de checbox
	//:::::: END GET CHECKBOX
	//-----> alert('num combobox '+ chklen +'   chekeado '+ chkchkd);
	//alert(chkbcs[0].checked);

	/*if(chkchkd==0){


	}*/

	if (chklen>0 && chkchkd==0 ){
		alert("No hay boletas seleccionadas a pagar");
		ok='no';
		return false;
	}

	if(chklen > 0 && chkchkd>0){
		//Si checkbox0 != True
		if(!chkbcs[0].checked && chklen ==1){
			alert("Debe seleccionar el Pago de la Boleta m\u00E1s Antigua.");
			ok='no';
			return false;
		}else{
			ok='si';
			if(!chkbcs[0].checked){
				alert("Debe seleccionar el Pago de la Boleta m\u00E1s Antigua.");//1
				ok='no';
				return false;
			}else{
				ok = 'si';
			}
			

			if(chkbcs[1] != null &&!chkbcs[1].checked && chkchkd > 1){
				alert("No puede Seleccionar y Pagar Boletas intercaladas."); //1
				ok='no';
				return false;
			}
			
			//Si el N° de checkbox es mayor a 2 y Si el N¼ de checkbox es != del N¼ de checkbox
			/*if( (chkchkd > 5) && (chklen != chkchkd)){
					alert("No puede Seleccionar y Pagar Boletas intercaladas.");
					ok='no';
					return false;
			}*/


			/*if ($("#nombre").val()==""){
					alert("Debe ingresar su nombre .");
					ok='no';
					return false;
			}*/

			/*if ($("#telefono").val()==""){
					alert("Debe ingresar su telefono o celular .");
					ok='no';
					return false;
			}*/

			/*if (!isEmailAddress($("#email").val())){
					alert("Debe ingresar su correo electronico valido .");
					ok='no';
					return false;
			}*/

			/*if($("input[name='forma_pago']:checked").length == 0){
					alert("Debe seleccionar medio de pago .");
					ok='no';
					return false;
			}else{
				$("#forma_pago_valor").val($("input[name='forma_pago']:checked").val());
			}*/

			/*if(!$("#webpay").is(':checked') && !$("#servipag").is(':checked') ){
					alert("Debe seleccionar medio de pago .");
					ok='no';
					return false;
			}

			if($("#webpay").is(':checked') && $("#servipag").is(':checked')){
					alert("Debe seleccionar solo un medio de pago .");
					ok='no';
					return false;
			}*/

			//if(ok=='si'){
				//document.operacion.action="http://172.16.68.85/cgi-bin/tbk_bp_pago.cgi";
				//document.operacion.action="http://172.16.68.85/pago-de-cuentas/redireccion-webpay/";
				//document.operacion.action="https://www.telefonicadelsur.cl/cgi-bin/tbk_bp_pago.cgi";
			document.operacion.action="http://app.telsur.cl/pago-de-cuentas/pago/";
			document.operacion.submit();
			//}

		}
	} else {
		//Si no hay checkbox, No hay Cuotas por pagar
		alert("No hay Boletas por pagar");
		ok='no';
	}
}

function total(){
	var tot=0;
	var doc_val = "";
	var indexAux=-1;
	$(".select").each(function(index,value){
		if ( $(this).attr("checked")) {
		var num_doc = $(this).attr("value");
			var total = $("#val_doc_"+num_doc).val();
			var tipo_doc = $("#tipo_doc_"+num_doc).val();
			var fecha_e_doc = $("#fecha_e_doc_"+num_doc).val();
			var fecha_v_doc = $("#fecha_v_doc_"+num_doc).val();
			//alert(tipo_doc);
			indexa=indexAux
			indexAux=index
			if ((indexAux-indexa)==1){
				tot+=parseFloat(total);
				doc_val+= num_doc+","+total+","+tipo_doc+","+fecha_e_doc+","+fecha_v_doc+"|";
			}else{
				alert("Debe Seleccionar las boletas de forma correlativa \n partiendo desde el pago m\u00E1s antiguo"); //1
				$(this).attr('checked', false);
			}
		}
	});
	$("#doc_valor").val(doc_val.substring(0, doc_val.length-1));
	$("#total").val(agregaPuntosFijo(tot));
	$("#TBK_MONTO").val(tot);
	// $("#TBK_MONTO").val(tot * 100);
}


/*



function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}*/
