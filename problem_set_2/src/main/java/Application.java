import java.util.ArrayList;

import rbs.flight.*;
import tree.*;
import tree.node.*;
import util.FlightCategory;
import util.searchable.FlightPriceLessFilter;

import java.util.Random;

import container.Container;

public class Application {
	private static Random random = new Random(69);
	
	public static String randId() {
		return Integer.toString(random.nextInt(1000));
	}

	public static void main(String[] args) {
		FlightTree flightTree = new FlightTree(new OneWayFlight("ROOT", "ROOT", "ROOT"));
		
		// Making Flights
		ArrayList<IFlight> flightList1 = new ArrayList<IFlight>(); // for first dualflight 
		ArrayList<IFlight> flightList2 = new ArrayList<IFlight>(); // for second dualflight
		ArrayList<IFlight> flightList3 = new ArrayList<IFlight>(); // for converting into nodes
		ArrayList<IFlight> flightList4 = new ArrayList<IFlight>(); // for converting into nodes

		flightList1.add(new OneWayFlight(randId(), "Shanghai 1", "Den Haag 1"));
		flightList1.add(new OneWayFlight(randId(), "Peking 2", "Hamburg 2"));
		flightList2.add(new OneWayFlight(randId(), "Berlin 3", "Wien 3"));
		flightList2.add(new OneWayFlight(randId(), "Hong Kong 4", "Melbourne 4"));
		flightList3.add(new OneWayFlight(randId(), "Paris 5", "London 5"));
		flightList3.add(new OneWayFlight(randId(), "Rom 6", "Ankara 6"));
		flightList3.add(new OneWayFlight(randId(), "Rotterdam 7", "Rennes 7"));
		flightList3.add(new OneWayFlight(randId(), "Madrid 8", "Lissabon 8"));
		
		DualFlight dualFlight1 = new DualFlight(randId()+"DF1", flightList1);
		DualFlight dualFlight2 = new DualFlight(randId()+"DF2", flightList2);
		flightList3.add(dualFlight1);
		flightList3.add(dualFlight2);
		
		flightList4.add(dualFlight1);
		flightList4.add(dualFlight2);
		DualFlight dualFlight = new DualFlight(randId()+"DF4", flightList4);
		
		flightTree.insertNode(new FlightTreeNode(dualFlight));
		for (IFlight flight : flightList3) {
			flightTree.insertNode(new FlightTreeNode(flight));
		}
		
		
		// Checking for coverage
		
		new FlightTree(flightList1.get(0));
		new FlightTree();
		flightTree.getRoot();
		flightTree.findNode(flightList3.get(5));
		flightTree.getRoot().findNodeByNode(flightTree.getRoot().getRightChild());
		FlightPriceLessFilter filter = new FlightPriceLessFilter();
		flightTree.searchByFilter(filter, new OneWayFlight(randId(), "Rotterdam 7", "Rennes 7", 80));
		flightTree.deepCopy();
		flightList3.get(0).deepCopy();
		flightList3.get(5).deepCopy();
		flightTree.getRoot().getChildren();
		System.out.println(flightTree.getRoot());
		CategoryTreeNode<IFlight, FlightCategory> copyNode = new CategoryTreeNode<IFlight, FlightCategory>(FlightCategory.DEFAULT);
		
		// Displaying nodes
		String displayString = flightTree.generateConsoleView("|___");
		System.out.println("TREE: ");
		System.out.println(displayString);
		System.out.println("\n\n");
		
		
		// Container
		Container<IFlight> container = new Container<IFlight>();
		container.addAll(flightList3);
		
		container.contains(flightList3.get(2));
		container.add(flightList3.get(0));
		container.get(3);
		container.containsAll(flightList3);
		container.toArray();
		
		System.out.println(container);
		
	}

}
