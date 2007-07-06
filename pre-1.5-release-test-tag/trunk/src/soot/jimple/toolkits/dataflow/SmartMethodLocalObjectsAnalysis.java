package soot.jimple.toolkits.dataflow;

import soot.*;
import java.util.*;
import soot.toolkits.graph.*;
import soot.jimple.*;

// SmartMethodLocalObjectsAnalysis written by Richard L. Halpert, 2007-02-23
// Uses a SmartMethodDataFlowAnalysis to determine if a Local or FieldRef is
// LOCAL or SHARED in the given method.

public class SmartMethodLocalObjectsAnalysis
{
	public static int counter = 0;
	static boolean printMessages;
	
	SootMethod method;
	DataFlowAnalysis dfa;
	SmartMethodDataFlowAnalysis smdfa;

	public SmartMethodLocalObjectsAnalysis(SootMethod method, DataFlowAnalysis dfa)
	{
		this.method = method;
		this.dfa = dfa;
		this.smdfa = dfa.getMethodDataFlowAnalysis(method);
		
		printMessages = dfa.printDebug();
		counter++;
	}
	
	public SmartMethodLocalObjectsAnalysis(UnitGraph g, DataFlowAnalysis dfa)
	{
		this(g.getBody().getMethod(), dfa);
	}
	
	public Value getThisLocal()
	{
		return smdfa.getThisLocal();
	}
	
	// 
	public boolean isObjectLocal(Value local, CallLocalityContext context) // to this analysis of this method (which depends on context)
	{
		EquivalentValue localEqVal;
		if(local instanceof InstanceFieldRef)
			localEqVal = dfa.getEquivalentValueFieldRef(method, ((FieldRef) local).getField());
		else
			localEqVal = new EquivalentValue(local);
			
		List sources = smdfa.sourcesOf(localEqVal);
		Iterator sourcesIt = sources.iterator();
		while(sourcesIt.hasNext())
		{
			EquivalentValue source = (EquivalentValue) sourcesIt.next();
			if(source.getValue() instanceof Ref)
			{
				if(!context.isFieldLocal(source))
				{
					if(printMessages)
						G.v().out.println("      Requested value " + local + " is LOCAL in " + method + " ");
					return false;
				}
			}
		}
		if(printMessages)
			G.v().out.println("      Requested value " + local + " is SHARED in " + method + " ");
		return true;
	}
	
	public static boolean isObjectLocal(DataFlowAnalysis dfa, SootMethod method, CallLocalityContext context, Value local)
	{
		SmartMethodDataFlowAnalysis smdfa = dfa.getMethodDataFlowAnalysis(method);

		EquivalentValue localEqVal;
		if(local instanceof InstanceFieldRef)
			localEqVal = dfa.getEquivalentValueFieldRef(method, ((FieldRef) local).getField());
		else
			localEqVal = new EquivalentValue(local);
			
		List sources = smdfa.sourcesOf(localEqVal);
		Iterator sourcesIt = sources.iterator();
		while(sourcesIt.hasNext())
		{
			EquivalentValue source = (EquivalentValue) sourcesIt.next();
			if(source.getValue() instanceof Ref)
			{
				if(!context.isFieldLocal(source))
				{
					if(printMessages)
						G.v().out.println("      Requested value " + local + " is LOCAL in " + method + " ");
					return false;
				}
			}
		}
		if(printMessages)
			G.v().out.println("      Requested value " + local + " is SHARED in " + method + " ");
		return true;
	}
}
