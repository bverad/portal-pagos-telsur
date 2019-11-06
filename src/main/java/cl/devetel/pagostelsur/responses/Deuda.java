package cl.devetel.pagostelsur.responses;

public class Deuda {
    
    private String doc;
    private String ven;
    private String emi;
    private String ref;
    private String mnt;
    private String nom;
    private String srv;
    
    public Deuda(){ }
    
    public Deuda(
        String doc,
        String ven,
        String emi,
        String ref,
        String mnt,
        String nom,
        String srv
    ) {
        this.doc = doc;
        this.ven = ven;
        this.emi = emi;
        this.ref = ref;
        this.mnt = mnt;
        this.nom = nom;
        this.srv = srv;
    }

    public String getDoc() {
        return doc;
    }
    public void setDoc(String doc) {
        this.doc = doc;
    }
    public String getVen() {
        return ven;
    }
    public void setVen(String ven) {
        this.ven = ven;
    }
    public String getEmi() {
        return emi;
    }
    public void setEmi(String emi) {
        this.emi = emi;
    }
    public String getRef() {
        return ref;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }
    public String getMnt() {
        return mnt;
    }
    public void setMnt(String mnt) {
        this.mnt = mnt;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getSrv() {
        return srv;
    }
    public void setSrv(String srv) {
        this.srv = srv;
    }

    @Override
    public String toString() {
        return "Deuda [doc : " + doc + ", ven : " + ven + ", emi : " + emi + ", ref : " + ref + ", mnt : " + mnt + ", nom : " + nom + ", srv : " + srv + "]";
    }
    
}
