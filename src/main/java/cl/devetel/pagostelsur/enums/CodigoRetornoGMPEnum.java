package cl.devetel.pagostelsur.enums;

/**
 * Enumeracion que contiene los codigos de retorno disponibles para la consulta a PDR.
 * @author bvera
 *
 */
public enum CodigoRetornoGMPEnum {
	
	CODE_00("00","Aprobada"),
	CODE_01("01","Suscriptor desconocido"),
	CODE_02("02","Suscriptor con problemas"),
	CODE_03("03","Monto de pago erróneo"),
	CODE_04("04","Error de proceso Módulo GW de Pago"),
	CODE_05("05","Sistema fuera de servicio"),
	CODE_06("06","Canal desconocido"),
	CODE_07("07","Canal suspendido"),
	CODE_08("08","Código tipo producto desconocido"),
	CODE_09("09","Código de producto desconocido"),
	
	CODE_10("10","Fecha no válida"),
	CODE_11("11","Error Formato: Campo Rut erróneo"),
	CODE_12("12","Error: Campo Id transacción no válido"),
	CODE_13("13","Transacción ya utilizada previamente"),
	CODE_14("14","Error Fraude: Rut en lista negra"),
	CODE_15("15","Error Fraude: Supera número máximo de pagos diarios"),
	CODE_16("16","Error Fraude: Supera monto de pago permitido"),
	CODE_17("17","Entrada incompleta faltan parámetros"),
	CODE_18("18","No hay datos para la consulta"),
	CODE_19("19","No hay datos para eliminar"),
	
	CODE_20("20","Solicitud de validación aprobada"),
	CODE_21("21","Solicitud de pago aprobada para medio de pago"),
	CODE_22("22","Error Formato: Campo Canal erróneo"),
	CODE_23("23","Error en base de datos"),
	CODE_24("24","Password de canal inválida"),
	CODE_25("25","Faltan datos del cliente"),
	CODE_26("26","Celular cliente, formato no válido"),
	CODE_27("27","Tipo de producto suspendido"),
	CODE_28("28","Producto suspendido"),
	CODE_29("29","Uno o mas productos no pueden ser agregados al carro de compra"),
	
	CODE_30("30","Cantidad total de producto y monto total no corresponde al detalle de la compra"),
	CODE_31("31","No hay productos para procesar"),
	CODE_32("32","Error al generar Id transacción"),
	CODE_33("33","Id transacción ya fue generado"),
	CODE_34("34","Error al registrar medio de pago de la transacción"),
	CODE_35("35","Error en la confirmación del pago de la transacción"),
	CODE_36("36","Error: Número de orden, ya registrada"),
	CODE_37("37","Error: Recibido desde medio de pago"),
	CODE_38("38","Error: Formato xml inválido"),
	CODE_39("39","Error: Medio de pago desconocido"),
	
	CODE_40("40","Error: Problemas en la configuración canal - medio de pago"),
	CODE_41("41","Entrada Incompleta, faltan parámetros en productos"),
	CODE_42("42","Error formato XML, productos inválidos"),
	CODE_43("43","Error: No fue posible desencriptar el xml"),
	CODE_44("44","Error: Nombre parámetro Post desconocido - canal"),
	CODE_45("45","Error: Nombre parámetro Post desconocido - medio de pago"),
	CODE_46("46","Error: Faltan parámetros Post desde medio de pago"),
	CODE_47("47","No hay medios de pago"),
	CODE_48("48","Transacción no aprobada por medio de pago"),
	CODE_49("49","Error en la validación de archivo de MAC"),
	
	CODE_50("50","Error intentando recuperar la información del medio de pago"),
	CODE_51("51","Error intentando recuperar la información del canal"),
	CODE_52("52","Error intentando registrar la información del medio de pago"),
	CODE_53("53","Error intentando registrar la información del pago Portal"),
	CODE_54("54","Transacción medio de pago registrada correctamente"),
	CODE_55("55","Transacción pago portal registrada correctamente"),
	CODE_56("56","Error intentando notificar el pago hacia el WS de portal"),
	CODE_57("57","Transacción rechazada por WS portal"),
	CODE_58("58","Transacción no válida desde WS portal"),
	CODE_59("59","Transacción no aprobada por portal"),

	CODE_60("60","Transacción WS portal registrada correctamente"),
	CODE_61("61","Transacción no existente desde medio de pago"),
	CODE_62("62","Error intentando registrar la información del WS Pago Portal"),
	CODE_63("63","Error intentando recuperar información de la tabla paramétrica"),
	CODE_64("64","Error Formato: Campo Password Canal erróneo"),
	CODE_65("65","Error Formato: Campo Página Respuesta Canal erróneo"),
	CODE_66("66","Error Formato: Campo Nombre Cliente erróneo"),
	CODE_67("67","Error Formato: Campo Dirección Cliente erróneo"),
	CODE_68("68","Error Formato: Campo Región Cliente erróneo"),
	CODE_69("69","Error Formato: Campo Email Cliente erróneo"),
	
	CODE_70("70","Error Formato: Campo Teléfono Fijo Cliente erróneo"),
	CODE_71("71","Error Formato: Campo Número Cliente erróneo"),
	CODE_72("72","Rechazada Error: Empresa desconocida"),
	CODE_73("73","Rechazada Error: Empresa suspendida"),
	CODE_74("74","Rechazada Error: Medio de pago desconocido"),
	CODE_75("75","Rechazada Error: Medio de pago suspendido"),
	CODE_76("76","Rechazada Error: Asociación empresa vs canal no válida"),
	CODE_77("77","Rechazada Error: Asociación empresa vs medio de pago no válida"),
	CODE_78("78","Rechazada Error: Asociación canal vs medio de pago no válida"),
	CODE_79("79","Rechazada Error: Tipo medio de pago desconocido"),
	
	CODE_80("80","Rechazada Error: Tipo medio de pago suspendido"),
	CODE_81("81","Rechazada Error: Tipo canal desconocido"),
	CODE_82("82","Rechazada Error: Tipo canal suspendido"),
	CODE_83("83","Rechazada Error: Asociación canal vs tipo canal no válida"),
	CODE_84("84","Rechazada Error: Asociación canal vs tipo producto no válida"),
	CODE_85("85","Rechazada Error: Asociación canal vs código producto no válida"),
	CODE_86("86","Rechazada Error: Asociación código producto vs tipo producto no válida"),
	CODE_87("87","Rechazada Error: Reversa medio de pago no fue realizada"),
	CODE_88("88","Rechazada Error: Reversa medio de pago realizada exitosamente"),
	CODE_89("89","Rechazada Transacción aprobada por portal"),
	
	CODE_90("90","Rechazada Error: Comunicación Reversa Crédito medio de pago"),
	CODE_91("91","Rechazada Error: Comunicación Solicitud Crédito medio de pago"),
	CODE_92("92","Rechazada Transacción no válida desde medio de pago"),
	CODE_93("93","Error Formato: Campo canal erróneo"),
	CODE_94("94","Error Formato: Campo orden de compra erróneo");
	
	String codigo;
	String descripcion;
	
	/**
	 * Retorna valor de enum segun codigo
	 * @param codigo
	 * @return
	 * @author bvera
	 * @since 02/05/2018
	 */
	public static CodigoRetornoGMPEnum getById(String codigo) {
	    for(CodigoRetornoGMPEnum e : values()) {
	        if(e.codigo.equals(codigo)) 
	        	return e;
	    }
	    
	    return null;
	}
	
	
	private CodigoRetornoGMPEnum(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
