import java.util.Random;
//import java.lang.Math;
import java.math.BigInteger;

public class RSA_1{
	BigInteger	n, e, p, q, d, phi, k = new BigInteger("2");
	int bitlength = 1024;
	
	Random rand = new Random();
	
	public RSA_1(){}
	
	public RSA_1(BigInteger n, BigInteger e){
		this.n = n;
		this.e = e;
	}
	
	public void randomPrime(BigInteger p, BigInteger q){
		this.p = p;
		this.q = q;
	}	
	
	public void keys(){
		System.out.println("Generating keys...");
		p = BigInteger.probablePrime(bitlength, rand);
		q = BigInteger.probablePrime(bitlength, rand);
		
		n = p.multiply(q);
		phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		e = BigInteger.probablePrime(bitlength/2, rand);
		
		while(phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0){
			e.add(BigInteger.ONE);
		}
		d = e.modInverse(phi);
		System.out.println("Keys Generated...");
	}
	
	/* public int gcd(int a, int b){
		int temp;
		while(true){
			temp = a%b;
			if(temp == 0)
				return b;
			a=b;
			b=temp;			
		}
	}
	
	public void generateKeys(){
		n = p*q;
		
		phi = (p-1)*(q -1);
		e = 2;
		
		while(e < phi){
			if(gcd(e, phi)==1){
				break;
			}else{
				e++;
			}	
		}
		
		k = 2; //constant value
		
		d = (1 + (k*phi))/e;
		System.out.print(d+" ");	
		
	} */
	
	public void publicKey(BigInteger n, BigInteger e){
		this.n = n;
		this.e = e;				
		
		p = BigInteger.probablePrime(bitlength, rand);
		q = BigInteger.probablePrime(bitlength, rand);
		n = p.multiply(q);
		phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		e = BigInteger.probablePrime(bitlength/2, rand);
		
		while(phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0){
			e.add(BigInteger.ONE);
		}
		d = e.modInverse(phi);
		System.out.println("Keys Generated...");
		
	}
	
	public String encrypt(String message){
		System.out.println("Encrypt: "+message);
		while(message.length() % 3 != 0){
			message+=" ";	
		}	
		
		int m[] = new int[message.length()];
		String encryptMessage = "", temp = "";
		
		char mes[] = message.toCharArray();
		int count = 0;
		
		for(int i = 0; i < message.length();i++){
			count++;
			message = message.toLowerCase();
			char ch = message.charAt(i);
			
			if(ch == ' '){
				m[i] = 0;	
			}else if(ch == '.'){
				m[i] = 27;
				
			}else if(ch == ':'){
				m[i] = 28;
			}else if(ch == '\''){
				m[i] = 28;
			}else if(ch == ','){
				m[i] = 30;
			}else
				m[i] = (int)ch - 96;
			
			temp = temp+m[i];
			if(count == 3){				
				BigInteger foo = new BigInteger(temp);
				System.out.println(temp);
				String str = ""+foo.modPow(e,n);

				count = 0;
				temp = "";
				encryptMessage += (str+" ");
				System.out.println("Encrypting message...");
			}										
		}
			System.out.println("\nEncrypted Message: ");
			System.out.print(encryptMessage+" ");
			System.out.println("\n");	
			return encryptMessage;	
	}
	
	public void decrypt(String message){
		System.out.println("\nDecrypted Message: ");
		
		String decryptedMessage = "";

		String arr[] = message.split("\\s+");
		//System.out.print(d+" ");
		BigInteger inc = new BigInteger("1");
		BigInteger ans;
		
		if(d == null){			
			do{
				ans = inc.multiply(n).add(new BigInteger("1")).mod(e);
				inc.add(new BigInteger("1"));
			}while(ans.compareTo(new BigInteger("0")) == 0);
			//d = ((inc.subtract(new BigInteger("1")).multiply(k)).add(new BigInteger("1"))).divide(e);
			d = inc;
			System.out.println("d: "+d);
			System.out.println("Encrypted Message: "+message+"\n\n");
		}
		for(int i = 0; i < arr.length; i++){			
			BigInteger temp = new BigInteger(arr[i]);
						
			String str = ""+temp.modPow(d, n);
			
			System.out.print(str+" ");
						
			decryptedMessage +=str+" ";
			
			int store[] = new int[3];
			
			for(int j = 0; j < 3; j++){
				
			}
		}				
	}

	public static void main(String[] args){
		RSA_1 rsa = new RSA_1();
		
		//rsa.randomPrime(61,53);
		//rsa.generateKeys();
		rsa.publicKey(new BigInteger(""+999797), new BigInteger(""+123457));
		//rsa.keys();
		String str = rsa.encrypt("Have a nice day");		
		rsa.decrypt(str);
	}
}