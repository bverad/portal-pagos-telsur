$(document).ready(function(e) {

	$('#txtRut').keypress(function (e) {
		var regex = new RegExp("^[a-zA-Z0-9]+$");
		var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
		if (regex.test(str)) {
			return true;
		}

		e.preventDefault();
		return false;
	});
	$('#txtRut').blur(function (e) {
		var initVal = $(this).val();
		outputVal = initVal.replace(/[^0-9a-zA-Z]/g,"");
		if (initVal != outputVal) {
			$(this).val(outputVal);
		}
		e.preventDefault();
		return false;
	});

	$("#tipo").val('rut');
	
	/*
	$("#telefono-movil-click div").html("");
	$("#telefono-fijo-click div").html("");
	$("#selservicio").val('');
	$("#txtRut").val('');
	$("#tipo").val('');

	$("#servicio").hide();
	$("#rut").hide();
	
	$("#selZona").selectbox();
	//$("#selArea").selectbox();

	$("#pbn-pasos-movil").hide();
	$("#pbn-pasos-fijo").hide();
	$("#cerrar").fadeOut("slow");
	*/

	$("#telefono-movil-click").click(function(){
		$("#telefono-movil-click div").html("<img src='img/check-ok.png' />");
		$("#telefono-fijo-click div").html("");
		$("#cerrar").fadeIn("slow");
		$("#rut").fadeIn("slow");
		$("#servicio").fadeOut("slow");
		$("#tipo").val('rut');
	});
	//cargo la imagen del ok y muestro la informacion correspondiente a donde clickea el usuario.

	$("#telefono-fijo-click").click(function(){
		$("#telefono-fijo-click div").html("<img src='img/check-ok.png' />");
		$("#telefono-movil-click div").html("");
		$("#rut").fadeOut("slow");
		$("#servicio").fadeIn("slow");
		$("#cerrar").fadeIn("slow");
		$("#tipo").val('servicio');
	});
	//desaparece el div y lmipia los campos check
	$("#cerrar-info-porta").click(function(){
		$("#cerrar").fadeOut("slow");
		$(".portar-numero").fadeOut("slow");
		$("#telefono-movil-click div").html("");
		$("#telefono-fijo-click div").html("");
	});
});

function lightbox(url,w,h){
		$.colorbox({
			href:url,
			width:w+'px', 
			height:h+'px', 
			iframe:true
		});
	}

	function setCookie(cname, cvalue, exdays) {
			var d = new Date();
			d.setTime(d.getTime() + (exdays*24*60*60*1000));
			var expires = "expires="+ d.toUTCString();
			document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
	}

	function getCookie(cname) {
		var name = cname + "=";
		var ca = document.cookie.split(';');
		for(var i = 0; i <ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0)==' ') {
				c = c.substring(1);
			}
			if (c.indexOf(name) == 0) {
				return c.substring(name.length,c.length);
			}
		}
		return "";
	}

	function checkCookie() {
		var cookie_encuesta_pc = getCookie("cookie_encuesta_pc");
		if (cookie_encuesta_pc == "") {
			setCookie("cookie_encuesta_pc", "encuesta-telsur", 1);
			poupEncuesta();
		}
	}

	function removeCookie() {
		document.cookie = "cookie_encuesta_pc=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
	}

	function poupEncuesta(){
		window.top.$.fn.colorbox({
			href:"/popup_encuesta.html",
			width:"500px", 
			height:"300px", 
			iframe:true
		});
	}