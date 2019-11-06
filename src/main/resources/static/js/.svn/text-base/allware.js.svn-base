
function recalcTotal() {
	var total = 0;
	$("table .check-amount").each(function(k, v) {
		total += (v.checked) ? parseInt(v.value) : 0;
	});
	return total;
}

function recalcTotalxs() {
	var total = 0;
	$("table .check-amount-xs").each(function(k, v) {
		total += (v.checked) ? parseInt(v.value) : 0;
	});
	return total;
}

function separatorsMiles(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

/**
 * select all checkbox in div child
 * 
 * @param {checkbox}
 *            item [checkbox item call]
 * @return --
 */
function selectAll(item) {

	var itemId = $(item).attr('id').split('-');
	
	//var empresaSeleccionada = $("#selectedCompany").val() == "TELSUR" ? 1 : 2;

	var checked = item.checked;
	if (checked) {
		//if($("#table-" + empresaSeleccionada).css('display') == "none"){
		if($("#table-" + itemId[1]).css('display') == "none"){
			$('#collapse-' + itemId[1] + ' .check-amount-xs').each(
					function(k, v) {
						$(v).prop('checked', true);
					});
			$('#total-amount').text(separatorsMiles(recalcTotalxs()));
		}else{
			$('#collapse-' + itemId[1] + ' .check-amount').each(function(k, v) {
				$(v).prop('checked', true);
			});
			$('#total-amount').text(separatorsMiles(recalcTotal()));
		}
		
		/*if (isMobile().any()) {
			$('#collapse-' + itemId[1] + ' .check-amount-xs').each(
					function(k, v) {
						$(v).prop('checked', true);
					});
			$('#total-amount').text(separatorsMiles(recalcTotalxs()));
		} else {
			$('#collapse-' + itemId[1] + ' .check-amount').each(function(k, v) {
				$(v).prop('checked', true);
			});
			$('#total-amount').text(separatorsMiles(recalcTotal()));
		}*/
	} else {
		if($("#table-" + itemId[1]).css('display') == "none"){
			$('#collapse-' + itemId[1] + ' .check-amount-xs').each(
					function(k, v) {
						$(v).prop('checked', false);
					});
			$('#total-amount').text(separatorsMiles(recalcTotalxs()));
		}else{
			$('#collapse-' + itemId[1] + ' .check-amount').each(function(k, v) {
				$(v).prop('checked', false);

			});

			$('#total-amount').text(separatorsMiles(recalcTotal()));
		}
		
		/*if (isMobile().any()) {
			$('#collapse-' + itemId[1] + ' .check-amount-xs').each(
					function(k, v) {
						$(v).prop('checked', false);
					});
			$('#total-amount').text(separatorsMiles(recalcTotalxs()));
		} else {
			$('#collapse-' + itemId[1] + ' .check-amount').each(function(k, v) {
				$(v).prop('checked', false);

			});

			$('#total-amount').text(separatorsMiles(recalcTotal()));
		}*/
	}
}

function isMobile() {
	var isMobile = {
		Android : function() {
			return navigator.userAgent.match(/Android/i);
		},
		BlackBerry : function() {
			return navigator.userAgent.match(/BlackBerry/i);
		},
		iOS : function() {
			return navigator.userAgent.match(/iPhone|iPad|iPod/i);
		},
		Opera : function() {
			return navigator.userAgent.match(/Opera Mini/i);
		},
		Windows : function() {
			return navigator.userAgent.match(/IEMobile/i);
		},
		any : function() {
			return (isMobile.Android() || isMobile.BlackBerry()
					|| isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
		}
	};

	return isMobile;
}
function getDocumentSelected() {
	var listDoc = [];
	var empresaSeleccionada = $("#selectedCompany").val() == "TELSUR" ? 1 : 2;
	console.log("verificando si el despliegue esta escalado");
	console.log("display value primer registro tabla...: " + $("#table-0").css('display'));
	console.log("display value primer registro tabla-xs: " + $("#table-0-xs").css('display'));
	//verifica si el primer registro esta o no escalado, segun estilo verifica si existen pagos seleccionados
	if($("#table-0").css('display') == "table"){
	//if($("#table-" + empresaSeleccionada).is(":visible") == true){
		console.log("conteo de documentos no responsivo");
		$("table .check-amount").each(
				function(k, v) {
					if (v.checked) {
						var customDocs = {};
						customDocs['nmro_documen'] = v
								.getAttribute('data-numdoc');
						customDocs['fech_documen'] = v
								.getAttribute('data-fecha');
						customDocs['fech_vencmto'] = v
								.getAttribute('data-fvenc');
						customDocs['valor'] = v.getAttribute('data-total');
						customDocs['tipo_doc'] = v
								.getAttribute('data-codigo-tipo-doc');
						customDocs['saldo'] = v.getAttribute('data-saldo');
						customDocs['desc_tipo_documen'] = v
								.getAttribute('data-desc-doc');
						customDocs['document_id'] = v
								.getAttribute('data-document_id');
						customDocs['io2'] = v
							.getAttribute('data-suctipo');
						listDoc.push(customDocs);
					}
				});
	} else {
		console.log("conteo de documentos responsivo");
		$("table .check-amount-xs").each(
				function(k, v) {
					if (v.checked) {
						var customDocs = {};
						customDocs['nmro_documen'] = v
								.getAttribute('data-numdoc');
						customDocs['fech_documen'] = v
								.getAttribute('data-fecha');
						customDocs['fech_vencmto'] = v
								.getAttribute('data-fvenc');
						customDocs['valor'] = v.getAttribute('data-total');
						//recibe codigo para insercion en base de datos
						customDocs['tipo_doc'] = v
								.getAttribute('data-codigo-tipo-doc');
						customDocs['saldo'] = v.getAttribute('data-saldo');
						customDocs['desc_tipo_documen'] = v
								.getAttribute('data-desc-doc');
						customDocs['document_id'] = v
								.getAttribute('data-document_id');
						customDocs['io2'] = v
								.getAttribute('data-suctipo');
						listDoc.push(customDocs);
					}
				});
	}
	
		  
	
	/*	
	if (!isMobile().any()) {
		console.log("conteo de documentos no responsivo");
		$("table .check-amount").each(
				function(k, v) {
					if (v.checked) {
						var customDocs = {};
						customDocs['nmro_documen'] = v
								.getAttribute('data-numdoc');
						customDocs['fech_documen'] = v
								.getAttribute('data-fecha');
						customDocs['fech_vencmto'] = v
								.getAttribute('data-fvenc');
						customDocs['valor'] = v.getAttribute('data-total');
						customDocs['tipo_doc'] = v
								.getAttribute('data-codigo-tipo-doc');
						customDocs['saldo'] = v.getAttribute('data-saldo');
						customDocs['desc_tipo_documen'] = v
								.getAttribute('data-desc-doc');
						customDocs['document_id'] = v
								.getAttribute('data-document_id');
						customDocs['io2'] = v
							.getAttribute('data-suctipo');
						listDoc.push(customDocs);
					}
				});
	} else {
		console.log("conteo de documentos responsivo");
		$("table .check-amount-xs").each(
				function(k, v) {
					if (v.checked) {
						var customDocs = {};
						customDocs['nmro_documen'] = v
								.getAttribute('data-numdoc');	
						customDocs['fech_documen'] = v
								.getAttribute('data-fecha');
						customDocs['fech_vencmto'] = v
								.getAttribute('data-fvenc');
						customDocs['valor'] = v.getAttribute('data-total');
						//recibe codigo para insercion en base de datos
						customDocs['tipo_doc'] = v
								.getAttribute('data-codigo-tipo-doc');
						customDocs['saldo'] = v.getAttribute('data-saldo');
						customDocs['desc_tipo_documen'] = v
								.getAttribute('data-desc-doc');
						customDocs['document_id'] = v
								.getAttribute('data-document_id');
						customDocs['io2'] = v
								.getAttribute('data-suctipo');
						listDoc.push(customDocs);
					}
				});
	}*/
	return listDoc;
}
/**
 * redirect url Pago
 * 
 * @return --
 */
function payment(e) {
	
	// validations
	var documents = getDocumentSelected();
	//var rut = $("#rut").text();
	var rut = $("#userId").val();
	var _token = $('input[name="_token"]').val();
	var company = $('#selectedCompany').val();
	var montoTotal = $("#total-amount").text();
	//convierte lista de cuentas a objeto json.
	var cuentas = JSON.stringify(documents);
	console.log(company);
	console.log(cuentas);
	console.log(encodeURI(cuentas));
	//valida si existe alguna deuda seleccionada, de lo contrario, envia mensaje
	if(documents.length == 0){
	    //mensaje
		$("#alert-cdeuda").show();
		$("#alert-cdeuda").html("<div class='alert alert-danger' role='alert'><span>Debe seleccionar documentos antes de efectuar un pago.</span></div>");
		$("#alert-cdeuda").delay(3000).fadeOut(500);
	}else{
		//envia cuentas y despliega medios de pago
		var form = $("#form_calcular_mp");
		form.append("<input type='hidden' id='rut' name='rut' value=" + rut + ' />');
		form.append("<input type='hidden' id='_token' name='_token' value=" + _token + ' />');
		form.append("<input type='hidden' id='company' name='company' value=" + company + ' />');
		form.append("<input type='hidden' id='montoTotal' name='montoTotal' value=" + montoTotal + ' />');
		form.append("<input type='hidden' id='cuentas' name='cuentas' value=" + encodeURI(cuentas) + ' />');
		form.submit();
		
	}

}

function requestWebPay() {


	var url = "";
	var idf = ""
	$('#btnPaymentTransbank').prop('disabled', true);
	var email = $("#email").val();
	
	//extraccion de valores segun el medio de pago seleccionado.
	$("div .pay-type").each(
		function(k, v) {
			if (v.checked) {
				url = v.getAttribute('data-url');
				idf = v.getAttribute('data-btnid');
			}
	});
	
	if(url == ""){
		$('#alert-mail').show();
		$("#alert-mail")
				.html(
						'<h2><strong>Error</strong> Debe seleccionar un medio de pago válido</h2>');
		$('#alert-mail').fadeIn();
		$('#btnPaymentTransbank').prop('disabled', false);
		$('#alert-mail').delay(3000).fadeOut(500);
		
		
	}else if (isValidEmailAddress(email)) {
		//verifica si transaccion existe, si ya existe, genera una nueva
		
		
		//actualiza mail para posterior envio
		var transaccion = $("#transaccion").val();
		actualizaMail(transaccion, email, idf);
		

		
	} else {
		$("#email").focus();
		$('#alert-mail').show();
		$("#alert-mail")
				.html(
						'<h2><strong>Error</strong> Debe Ingresar un email válido</h2>');
		$('#alert-mail').fadeIn();
		$('#alert-mail').delay(3000).fadeOut(500);
		$('#btnPaymentTransbank').prop('disabled', false);
		
	}
}

/**
 * actualiza mail de cliente para notificacion posterior a pago.
 * @param transaccion
 * @param mail
 * @returns
 */
function actualizaMail(transaccion,mail,idf){
	
	var search = {
		transaccion : transaccion,
		mail : mail
	};
	
	var status = 200;
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e,xhr,options) {
		xhr.setRequestHeader(header, token);
	});
	
	console.log("Iniciando actualizacion de mail de cliente");
	
	$.ajax({
		//type : 'POST',
		type : 'GET',
		contentType: "application/json",
		url : '/pagos-telsur/api/pagos/actualizar_mailcliente?transaccion=' + transaccion + '&mail=' + mail ,
		//url : '/pagos-telsur/api/pagos/actualizar_mailcliente',
		//data: JSON.stringify(search),
		dataType: 'json',
	    cache: false,
	    timeout: 600000,
		success : function(data, textStatus, jqXHR) {
			console.log(data.mensaje);
			console.log(jqXHR.status);
			status = jqXHR.status;
			abrirVentanaMedioPago(status,idf)
		},
        error: function (e) {
        	console.log("Error no controlado al ejecutar servicio de actualizacion de mail de cliente : ", e);
        	console.log(e.status);
        	status = e.status;
        	abrirVentanaMedioPago(status,idf)
        	
        }
        
	});
	
	return status;

}

/**
 * Abre ventana de medio de pago en el caso de que las solicitudes se ejecuten exitosamente (en el tiempo definido en el que dura el intervalo de sesion).
 * @param transaccion
 * @param mail
 * @returns
 */
var my_window;
function abrirVentanaMedioPago(status,idf){
	//retornara un estado distinto si la sesion caduca, de ser asi redirigira a ventana de inicio
	if(status === 200){
		//implementa pago.
		//bloquea la pantalla mientras se este efectuando un pago.
		$('#page-cover_body').css('pointer-events','none');
		$('#page-cover_body').css('cursor','default');
		//establece valores de tamaño de pantalla
		var w = screen.width / 2; 
		var h = screen.height / 2; 
		var x = w - w / 2; 
		var y = h - h / 2; 
		var form = document.getElementById('form_'+idf);  
		var al = $("#mpAlto" + idf).val(); 
		var an = $("#mpAncho" + idf).val();
		
		if ((al.length == 0) || (an.length == 0)){ 
			al = h; an = w; 
		} 
		
		if(al > h){ 
			y = y/2; 
		} 
		
		if(an > w){ 
			x = x/2; 
		} 
		
		$('#page-cover_body').addClass("disabledbutton");
		if( (idf*1)===69){ 
			my_window = window.open("", "ventanaForm");
		}else{ 
			//identificar IE9 - enviar previamente los datos a formulario
			my_window = window.open("", "ventanaForm", "width="+an+",height="+al+",left="+x+",top="+y);
			//return false;
			
		} 
		
		form.submit(); 
		my_window.focus();
		
	}else{
		console.log("Sesion finalizada, redirigiendo a ventana de consulta...")
		$("#mensaje").val("La sesion expiró, consulte nuevamente");
		$("#se-form").submit();
	}
}

/**
 * 
 * @param url
 * @param respuestaCre
 * @returns
 */
function recargaVocher(url, respuestaCre){ 
	$("#respuestaCredito").val(respuestaCre); 
	document.getElementById("form_r").action = url; 
	document.getElementById('form_r').submit(); 
	my_window.close(); 
}

/**
 * validations selection doc in view documents
 * 
 * @param {checkbox}
 *            item [checkbox item call]
 * @return {[type]} [description]
 */
function validateSelectionDocXs(item) {

	var numDoc = item.getAttribute('data-numdoc');
	var docId = item.getAttribute('data-docid');
	var fecha = item.getAttribute('data-fecha');
	var fvenc = item.getAttribute('data-fvenc');
	var succodigo = item.getAttribute('data-succodigo');
	var succorrelativo = item.getAttribute('data-succorrelativo');
	var checked = item.checked

	var fechaCheckedItem = item.getAttribute('data-fecha');

	var tableId = "table-" + succorrelativo + "-xs";
	var error = false;
	$("#" + tableId + ' .check-amount-xs')
			.each(
					function(k, v) {

						var fecha = $(v)[0].getAttribute('data-fecha');
						if (moment(fechaCheckedItem, 'DD-MM-YYYY', true)
								.format() <= moment(fecha, 'DD-MM-YYYY', true)
								.format()) {

							if (!$(item).prop('checked')) {
								$(v).prop('checked', false);
							}
							error = false;
							return true;
						} else {
							if (!$(v).prop('checked')) {
								error = true;
								$(item).prop('checked', false);
								$(v).prop('checked', false);
								return false;
							}
						}

					});

	if (error) {
		var parent = $("#" + tableId).parent().parent().parent();
		var errorDiv = parent.find('.alert');

		$(errorDiv)
				.html(
						'<a class="close" onclick="closeDiv(this)">×</a><h2><strong>Error</strong> Debe seleccionar los documentos con fecha más antigua</h2>');
		$(errorDiv).removeClass('hidden');
		// $(errorDiv).show();

	} else {
		var parent = $("#" + tableId).parent().parent().parent();
		var errorDiv = parent.find('.alert');

		$(errorDiv).addClass('hidden');
	}
}

function validateSelectionDoc(item) {

	var numDoc = item.getAttribute('data-numdoc');
	var docId = item.getAttribute('data-docid');
	var fecha = item.getAttribute('data-fecha');
	var fvenc = item.getAttribute('data-fvenc');
	var succodigo = item.getAttribute('data-succodigo');
	var succorrelativo = item.getAttribute('data-succorrelativo');
	var checked = item.checked

	var fechaCheckedItem = item.getAttribute('data-fecha');
	var tableId = "table-" + succorrelativo;
	var error = false;
	
	$("#" + tableId + ' .check-amount')
			.each(
					function(k, v) {
						var fecha = $(v)[0].getAttribute('data-fecha');
						if (moment(fechaCheckedItem, 'DD-MM-YYYY', true)
								.format() <= moment(fecha, 'DD-MM-YYYY', true)
								.format()) {
							if (!$(item).prop('checked')) {
								$(v).prop('checked', false);
							}
							error = false;

							return true;
						} else {
							if (!$(v).prop('checked')) {
								error = true;
								$(item).prop('checked', false);
								$(v).prop('checked', false);

								return false;
							}
						}
					});

	
	if (error) {
		var parent = $("#" + tableId).parent().parent().parent();
		var errorDiv = parent.find('.alert');
		
		$(errorDiv)
				.html(
						'<a class="close" onclick="closeDiv(this)">×</a><h2><strong>Error</strong> Debe seleccionar los documentos con fecha más antigua</h2>');
		$(errorDiv).removeClass('hidden');
		// $(errorDiv).show();

	} else {
		var parent = $("#" + tableId).parent().parent().parent();
		var errorDiv = parent.find('.alert');

		$(errorDiv).addClass('hidden');
	}
}

function closeDiv(itemId) {

	var parent = $(itemId).parent();
	parent.addClass('hidden');
}

function isValidEmailAddress(emailAddress) {
	var pattern = new RegExp(
			/^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i);
	// alert( pattern.test(emailAddress) );
	return pattern.test(emailAddress);
};

function scrollingMobile() {
	if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i
			.test(navigator.userAgent)) {
		$('html, body').animate({
			scrollTop : $("#container-scrolling").offset().top
		}, 1);
	}
}

window.onload = function() {
	scrollingMobile();
}

function isValidSelected(obj) {
	var error = false;
	obj.forEach(function(key) {
		if (key.document_id != obj[0].document_id) {
			error = true;
		}
	});
	console.log(obj);

	if (error) {
		$('#alert-mail').show();
		$("#alert-mail")
				.html(
						'<a class="close" onclick="closeDiv(this)">×</a>'
								+ '<h2><strong>Error</strong>'
								+ '<p>Para este medio de pago </p>'
								+ '<p>no es posible seleccionar múltiples grupos de facturación</p></h2>');

		$('#btnPaymentTransbank').prop('disabled', true);
		$('#alert-mail').delay(3000).fadeOut(500);

	}
}

function enableButtonPayment() {

	$('#btnPaymentTransbank').prop('disabled', false);
}

/**
 * 1carga inicial de registros por empresa
 * 
 * @return {[type]} lista de registros por empresa segun existencia.
 */
function cargaInicialRegistros(){
	
	var registrosTelsur = parseInt($('#cantidadRegistrosTelsur').val());
	var registrosTelcoy = parseInt($('#cantidadRegistrosTelcoy').val());
	if(registrosTelsur > 0 && registrosTelcoy > 0){		
		cargaRegistros("TELSUR");
	}else if(registrosTelsur > 0 && registrosTelcoy == 0){
		/*$('#btn-telsur').attr("class","btn btn-success btn-company");
		$('#btn-telcoy').css("display","none");*/
		
		$('button.btn-company[value="TELSUR"]').attr("class","btn btn-success btn-company");
		$('button.btn-company[value="TELCOY"]').css("display","none");
		
		
		cargaRegistros("TELSUR");
	}else if(registrosTelsur == 0 && registrosTelcoy > 0){
		/*$('#btn-telcoy').attr("class","btn btn-success btn-company");
		$('#btn-telsur').css("display","none");*/

		$('button.btn-company[value="TELCOY"]').attr("class","btn btn-success btn-company");
		$('button.btn-company[value="TELSUR"]').css("display","none");
		
		cargaRegistros("TELCOY");
	}  
}

/**
 * redirige a comprobantes agregando parametros
 * 
 * @param rut 
 * @param empresaSeleccionada
 * @author bverad
 * @since 14/11/2018
 */
function cargaComprobantes(){
	//var host = window.location.host;
	var rut = $("#userId").val();
	var empresaSeleccionada = $("#selectedCompany").val();
	
	//window.location.href = 'http://' + host + '/pagos-telsur/pagos/comprobantes?rut=' + rut + '&empresa=' + empresaSeleccionada ;
	window.location.href = '/pagos-telsur/pagos/comprobantes?rut=' + rut + '&empresa=' + empresaSeleccionada;
}

/**
 * validations selection doc in view documents
 * 
 * @param {hidden}
 *            empresa [empresa ]
 * @return {[type]} [lista de registros por empresa segun existencia.]
 */
function cargaRegistros(empresa){
	//setea valor  para canal
	$('#selectedCompany').val(empresa);
	console.log("valor empresa seleccionada : " + empresa);
	$('.panel-default').each(function(idx, value) {	
		if(empresa === "TELSUR"){
			//limpia registros en el caso de cambio de seleccion
			limpiaPagosSeleccionados();
			//setea estilo de los botones
			/*$('#btn-telsur').attr("class","btn btn-success btn-company");
			$('#btn-telcoy').attr("class","btn btn-default btn-company");*/
			
			$('button.btn-company[value="TELSUR"]').attr("class","btn btn-success btn-company");
			$('button.btn-company[value="TELCOY"]').attr("class","btn btn-default btn-company");
			
			if ($(value).attr("data-empresa") === "TELSUR"){
				$(value).css("display","block");
			}else{
				$(value).css("display","none");
			}
		}else{
			//limpia registros en el caso de cambio de seleccion
			limpiaPagosSeleccionados();
			//setea estilo de los botones
			/*$('#btn-telcoy').attr("class","btn btn-success btn-company");
			$('#btn-telsur').attr("class","btn btn-default btn-company");*/
			
			$('button.btn-company[value="TELCOY"]').attr("class","btn btn-success btn-company");
			$('button.btn-company[value="TELSUR"]').attr("class","btn btn-default btn-company");
			
			
			if ($(value).attr("data-empresa") === "TELCOY"){
				$(value).css("display","block");
			}else{
				$(value).css("display","none");
			}
		}
	});
}

/**
 * Limpia valores previamente seleccionados en el caso de que se cambie de empresa al seleccionar 
 * dicumentos a pagar
 * @returns
 * @author bverad
 */
function limpiaPagosSeleccionados(){
	$('input[type=checkbox]').each(function () {
		$('table .check-amount').prop('checked', false);
		$('.check-all').prop('checked', false);
	});	
	$('#total-amount').text("0");
}



const urlMaps = {};  // cache
/**
 * Permite insertar imagenes en un screenshot
 * @param url
 * @returns
 */
function getDataUri(url) {

  return new Promise(function(resolve, reject) {
    if (urlMaps[url]) {
      return urlMaps[url]; // cache because I dont want to calculating same URL again and again
    } else {
      const image = new Image();

      image.onload = function () {
        const canvas = document.createElement('canvas');
        canvas.width = this.naturalWidth; // or 'width' if you want a special/scaled size
        canvas.height = this.naturalHeight; // or 'height' if you want a special/scaled size

        canvas.getContext('2d').drawImage(this, 0, 0);
        const dataURI = canvas.toDataURL('image/png');
        // cache loaded images
        urlMaps[url] = dataURI;
        // on success
        resolve(dataURI);
      };

      image.onerror = function() {
        // on failure
        reject('Error Loading Image');
      }

      image.setAttribute('crossOrigin', 'anonymous')
      image.src = url;
    }
  });
}


$(document).ready(function() {

	$('.check-amount').prop('checked', false);
	$('.check-amount-xs').prop('checked', false);
	$("#pdfDownloader").click(function(e){
		    	
		var doc = new jsPDF('p', 'mm', [330, 450]); //210mm wide and 297mm high
		html2canvas($("#container"), 
				{
					foreignObjectRendering:true,  
					logging:true, 
					letterRendering:true,
					removeContainer: true
				}).then(function(canvas) {
			        var x = canvas.toDataURL("image/png", 0.95);
			        getDataUri("/pagos-telsur/img/bg-pago-cuentas-login_2.png").then(function(imgData){
			        	doc.addImage(imgData, "PNG", 1, 1, 330, 450);
			        });
			        doc.addImage(x, "PNG", 1, 1, 330, 450);
			        doc.save("voucher.pdf");
				}
			);

	});
	
	
	//carga inicial de registros
	cargaInicialRegistros()
	
	$('.check-amount').click(function() {
		$('#total-amount').text(separatorsMiles(recalcTotal()));
	});

	$('.check-amount-xs').click(function() {
		$('#total-amount').text(separatorsMiles(recalcTotalxs()));
	});

	// redireccionamiento a filtro por empresa.
	$('.btn-company').click(function() {
		var empresa = this.value;			
		cargaRegistros(empresa);
	});
	
	//redireccion a pantalla de botones de pago.
	$('.btn-mp').on('click', function() {
		var $this = $(this);
		//desactiva boton siguiente si el monto es mayor a 0.
		var montoTotal = parseInt($("#total-amount").text(), 10);
		if(montoTotal > 0){
			$(this).attr("disabled", true);
			var loadingText = '<i class="fa fa-circle-o-notch fa-spin"></i> Cargando...';
			if ($(this).html() !== loadingText) {
			  $this.data('original-text', $(this).html());
			  $this.html(loadingText); 
			  //bloquea posibilidad de ver comprobantes
			  $(".btn-history").attr("disabled", true);
			  /*setTimeout(function() {
			  $this.html($this.data('original-text'));
			    }, 2000);*/
			}
		
		}
	  //submit de data de cuentas seleccionadas hacia medios de pago
	  payment();
		
	});


	$(".pay-type").on('click', function() {
		
		var box = $(this);
		if (box.is(":checked")) {
			var group = "input:checkbox[name='" + box.attr("name") + "']";
			$(group).prop("checked", false);
			box.prop("checked", true);
		} else {
			box.prop("checked", false);
		}

		var flag = false;
		$.each($(".pay-type"), function(index, value) {
			if (!$(value).is(':checked')) {
				flag = true;
			}
		});

		if (flag) {
			var group = "input:checkbox[name='" + box.attr("name") + "']";
			$(group).prop("checked", false);
			box.prop("checked", true);
		}
	});
});