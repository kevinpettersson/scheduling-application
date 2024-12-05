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


<div 
	class="flex h-screen flex-col items-center justify-center view9" 
	>
	<h1 class="mb-6 font-bold" style="font-size: 4rem; color: black; margin-bottom: 16rem;"
	>
		TimeBridge
	</h1>

	<form method="GET" use:enhance>
		<Form.Field {form} name="url">
			<Form.Control>
				{#snippet children({ props })}
					<div
						class="flex flex-row items-center gap-3 rounded-xl border p-2 pl-3 ring-zinc-700 focus-within:ring"
						style="margin-bottom: 8rem;"
					>
						<Input
							{...props}
							class="focus-visible:ring-none h-8 w-96 rounded-sm border-0  px-1  focus:ring-transparent"
							bind:value={$formData.url}
							placeholder="https://cloud.timeedit.net/chalmers/web/public/ ... .ics"
						/>
						<Form.Button size="icon"><Upload /></Form.Button>
					</div>
				{/snippet}
			</Form.Control>
			<div style="min-height: 4rem; margin-top: -6rem; display: flex; align-items: center;">
				<Form.FieldErrors />
			</div>
		</Form.Field>
	</form>
</div>



