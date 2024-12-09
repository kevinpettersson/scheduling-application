<script lang="ts">
	import * as Drawer from '$lib/components/ui/drawer/index.js';
	import { Button, buttonVariants } from '$lib/components/ui/button/index.js';
	import { Label } from '$lib/components/ui/label/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { Copy } from 'lucide-svelte';
	import { downloadCalendar } from '$lib/api.svelte';
	import { calendar } from '$lib/store.svelte';
	import Separator from '../ui/separator/separator.svelte';

	const API_BASE_URL = import.meta.env.VITE_PUBLIC_API_BASE_URL || 'http://localhost:8080';
</script>

<Drawer.Root>
	<Drawer.Trigger class={`${buttonVariants({ variant: 'default' })} flex-grow`}>
		Export Calendar
	</Drawer.Trigger>
	<Drawer.Content>
		<div class="mx-auto w-full max-w-sm">
			<Drawer.Header>
				<Drawer.Title>Export Calendar</Drawer.Title>
				<Drawer.Description>
					You can either subscribe to this calendar using the link below or download it directly.
				</Drawer.Description>
			</Drawer.Header>
			<div class="flex flex-col space-y-4 px-4 pb-0">
				<div class="flex items-center space-x-2">
					<div class="grid flex-1 gap-2">
						<Label for="link" class="sr-only">Link</Label>
						<Input id="link" value="{API_BASE_URL}/public/{calendar.id}" readonly />
					</div>
					<!-- <Button variant="outline" size="icon" class="px-3">
						<span class="sr-only"> Copy </span>
						<Copy class="h-4 w-4" />
					</Button> -->
				</div>
				<Separator />
			</div>
			<Drawer.Footer>
				<button
					on:click={downloadCalendar}
					class={`${buttonVariants({ variant: 'default' })} flex-grow`}
				>
					Download
				</button>
				<Drawer.Close>
					<Button variant="ghost" class="w-full">Close</Button>
				</Drawer.Close>
			</Drawer.Footer>
		</div>
	</Drawer.Content>
</Drawer.Root>
