import { z } from 'zod';

// Import Schema
export const _importSchema = z.object({
	url: z
		.string()
		.url('Please provide a valid URL.')
		.startsWith(
			'https://cloud.timeedit.net/',
			'Please ensure you are using a TimeEdit link.'
		)
		// Use a regex match to allow query params after .ics
		.regex(/\.ics(\?.*)?$/, 'Ensure you are providing a valid iCalendar file link.'),
		
});

export type importSchema = typeof _importSchema;

// Export Schema
 