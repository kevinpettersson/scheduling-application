import { z } from 'zod';

// Schema for the iCal URL that is used to fetch the schedule
export const _iCalSchema = z.object({
	url: z
		.string()
		.url('Please provide a valid URL.')
		.startsWith(
			'https://cloud.timeedit.net/',
			'Please ensure you are using a TimeEdit link.'
		)
		.endsWith(
			'.ics',
			'Ensure you are providing a valid iCalendar file link.'
		),
});

export type iCalSchema = typeof _iCalSchema;

// Schema for filtering events by course code
export const _codeSchema = z.object({
	codes: z
		.array(z.string().min(1))
		.min(1, "Please select at least one course."),
});

export type codeSchema = typeof _codeSchema;