<script lang="ts">
    import { buttonVariants } from "$lib/components/ui/button/index.js";
    import { Input } from "$lib/components/ui/input/index.js";
    import { Label } from "$lib/components/ui/label/index.js";
    import * as Popover from "$lib/components/ui/popover/index.js";
   </script>
    
   <Popover.Root>
    <Popover.Trigger class={buttonVariants({ variant: "outline" })}
     >Open</Popover.Trigger
    >
    <Popover.Content class="w-80">
     <div class="grid gap-4">
      <div class="space-y-2">
       <h4 class="font-medium leading-none">Attendee</h4>
      </div>
      <div class="grid gap-2">
       <div class="grid grid-cols-3 items-center gap-4">
        <Label for="name">Name</Label>
        <Input id="name" value="name!" class="col-span-2 h-8" />
       </div>
       <div class="grid grid-cols-3 items-center gap-4">
        <Label for="email">Email</Label>
        <Input id="email" value="email!" class="col-span-2 h-8" />
       </div>
      </div>
     </div>
    </Popover.Content>
   </Popover.Root>