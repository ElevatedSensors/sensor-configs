---
layout: default
title: ESP Web Tools
header_type: post
show_breadcrumb: false
---

# Installation

You can use the options below to install the pre-built firmware directly to your device via USB from the browser. Note this has already been done from the factory. You only need to do this if you're trying to start fresh, or grab the latest updates.

You can also use it to get device info, check which version is installed, view logs, change Wi-Fi, or add to Home Assistant.

## Select Device

<!-- Bed Presence Mk1 (Default Device) -->
<div class="form-check">
    <input class="form-check-input" type="radio" name="deviceType" value="bpmk1" id="deviceType1" checked>
    <label class="form-check-label" for="deviceType1">
        Bed Presence Mk1
    </label>
</div>

<!-- Add next device -->
<!-- <div class="form-check"> -->
<!--     <input class="form-check-input" type="radio" name="deviceType" value="test" id="deviceType2"> -->
<!--     <label class="form-check-label" for="deviceType2"> -->
<!--         Test Device -->
<!--     </label> -->
<!-- </div> -->

<p class="button-row" align="center">
    <!-- specify default manifest -->
    <esp-web-install-button class="" manifest="https://docs.elevatedsensors.com/bpmk1-manifest.json"></esp-web-install-button>
</p>

<!-- ESPWebTools script -->
<script type="module" src="https://unpkg.com/esp-web-tools@10/dist/web/install-button.js?module"></script>

<!-- Update manifest based on selected device -->
<script>
  document.querySelectorAll('input[name="deviceType"]').forEach(radio =>
    radio.addEventListener("change", () => {
      const button = document.querySelector('esp-web-install-button');
      button.manifest = `https://docs.elevatedsensors.com/${radio.value}-manifest.json`;
    }
  ));
</script>
