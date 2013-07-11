package org.zlzh.util;


/**
 * <p>Title:A utility class to store, apply, and unapply escape mappings to Strings</p>
 * @author:  zenglizhi
 * @time:    2013-6-9
 * @version:  v1.0
 */
public class Escaper {

	private final Mapping[] nonOverlapMappings; // unescaped -> escaped

	private final Mapping[] specialBackMappings; // escaped -> unescaped
	private final Mapping[] normalBackMappings; // escaped -> unescaped

	/**
	 * Construct with the mappings (in order) to apply.
	 * <p>
	 * NOTE: The first mappings up to normalSetIndex are mappings form escape
	 * symbols themselves that cannot be applied in an overlapping fashion. The
	 * remainder of the mappings will be applied in an overlapping way.
	 * <p>
	 * 
	 * @param mappings
	 *            Strings such that mappings[i][0] is the string whose escaped
	 *            mapping is mappings[i][1].
	 * @param normalSetIndex
	 *            The index in the mappings at which the normal set of
	 *            overlappable mappings begins (as opposed to the escaping of
	 *            escape symbols that need to be applied first without overlap.
	 */
	public Escaper(String[][] mappings, int normalSetIndex) {
		this.nonOverlapMappings = new Mapping[mappings.length];
		this.specialBackMappings = new Mapping[normalSetIndex];
		this.normalBackMappings = new Mapping[mappings.length - normalSetIndex];

		for (int i = 0; i < mappings.length; ++i) {
			char[] mappings0 = mappings[i][0].toCharArray();
			char[] mappings1 = mappings[i][1].toCharArray();

			if (i < normalSetIndex) {
				this.specialBackMappings[i] = new Mapping(mappings1, mappings0);
			} else {
				this.normalBackMappings[i - normalSetIndex] = new Mapping(
						mappings1, mappings0);
			}
			this.nonOverlapMappings[i] = new Mapping(mappings0, mappings1);
		}
	}

	/**
	 * Apply the escape mappings (forward).
	 */
	public String escape(String string) {
		return applyMappings(nonOverlapMappings, string);
	}

	/**
	 * Unapply the escape mappings (backward).
	 */
	public String unescape(String string) {
		String result = applyMappings(normalBackMappings, string);
		return applyMappings(specialBackMappings, result);
	}

	private String applyMappings(Mapping[] mappings, String string) {
		StringBuffer result = new StringBuffer();

		if (string != null && string.length() > 0) {
			char[] stringChars = string.toCharArray();
			for (int stringIndex = 0; stringIndex < stringChars.length; ++stringIndex) {

				boolean mapped = false;
				for (int mappingIndex = 0; mappingIndex < mappings.length; ++mappingIndex) {
					Mapping mapping = mappings[mappingIndex];

					boolean matches = true;
					for (int sourceIndex = 0; sourceIndex < mapping.source.length
							&& stringIndex + sourceIndex < stringChars.length; ++sourceIndex) {
						if (stringChars[stringIndex + sourceIndex] != mapping.source[sourceIndex]) {
							matches = false;
							break;
						}
					}

					if (matches) {
						mapped = true;
						result.append(mapping.dest);
						stringIndex += (mapping.source.length - 1);
						break;
					}
				}

				if (!mapped) {
					result.append(stringChars[stringIndex]);
				}
			}
		}

		return result.toString();
	}

	private class Mapping {
		private final char[] source;
		private final char[] dest;

		public Mapping(char[] source, char[] dest) {
			this.source = source;
			this.dest = dest;
		}
	}
}
