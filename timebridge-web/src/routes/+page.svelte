<script lang="ts">
	import * as Form from '$lib/components/ui/form/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { superForm, defaults, setError } from 'sveltekit-superforms';
	import { zod } from 'sveltekit-superforms/adapters';
	import { _iCalSchema, type iCalSchema } from '$lib/schemas';
	import { goto } from '$app/navigation';
	import { calendarStore } from '$lib/stores/session-store';
	import { Upload } from 'lucide-svelte';

	const form = superForm(defaults(zod(_iCalSchema)), {
		SPA: true,
		validators: zod(_iCalSchema),
		async onUpdate({ form }) {
			// Call an external API with form.data, await the result and update form
			if (form.valid) {
				let request = `http://localhost:8080/upload?ical=${form.data.url}`;

				const response = await fetch(request, {
					method: 'GET',
					headers: {
						'Content-Type': 'application/json'
					}
				});

				if (!response.ok) {
					throw new Error(`Failed to upload calendar: ${response.statusText}`);
				} else {
					calendarStore.set(await response.json());
					goto('/editor');
				}
			}
		}
	});

	const { form: formData, enhance } = form;
</script>

<div class="flex h-screen flex-col items-center justify-center">
	<h1 class="mb-6 font-bold">TimeBridge</h1>
	<form method="GET" use:enhance>
		<Form.Field {form} name="url">
			<Form.Control>
				{#snippet children({ props })}
					<div class="flex flex-row gap-3 rounded-xl border p-2 pl-3 items-center">
						<Input
							{...props}
							class="h-8 w-96 border-0 focus-visible:ring-ring rounded-sm px-1"
							bind:value={$formData.url}
						/>
						<Form.Button size="icon"><Upload /></Form.Button>
					</div>
				{/snippet}
			</Form.Control>
			<Form.FieldErrors />
		</Form.Field>
	</form>
</div>
