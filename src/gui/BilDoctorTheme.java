package gui;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class BilDoctorTheme extends FlatIntelliJLaf {
	public static final String NAME = "BilDoctorTheme";

	public static boolean setup() {
		return setup( new BilDoctorTheme() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, BilDoctorTheme.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
