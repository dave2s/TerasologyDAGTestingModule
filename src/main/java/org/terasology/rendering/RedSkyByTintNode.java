/*
 * Copyright 2019 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.rendering;

import org.terasology.context.Context;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.rendering.dag.gsoc.DependencyConnection;
import org.terasology.rendering.dag.gsoc.ModuleRendering;
import org.terasology.rendering.dag.gsoc.NewNode;
import org.terasology.rendering.dag.nodes.TintNode;

// TODO NewAbstractNode to NewNode only!, no NewAbstractNode in here
// TODO Decide on what must by marked as @API or whitelisted (like nodes and such...use renderGraphAPI to access main DAG)

public class RedSkyByTintNode extends ModuleRendering {

    private static int initializationPriority = 10;

    public RedSkyByTintNode(Context context) {
        super(context);
        setInitPriority(initializationPriority);
    }

    public void initialise() {
        super.initialise();
        // TODO hide this..reflection?
           // super.setProvidingModule(this.getClass());
        tintBackdropRed();
    }

    private void tintBackdropRed() {
        // Create a new tintNode
        renderGraph.addShader("tint", providingModule);
        NewNode tintNode = new TintNode("tintNode", providingModule, context);
        renderGraph.addNode(tintNode);

        // renderGraph.reconnectInputFboToOutput(renderGraph.findAka("backdrop"), 1, tintNode, 1, true);
        NewNode backdrop = renderGraph.findAka("backdrop");
        DependencyConnection backdropFboOutput = backdrop.getOutputFboConnection(1);
        renderGraph.connectFbo(backdrop, 1, tintNode, 1);
        renderGraph.reconnectAllConnectedInputsTo(backdropFboOutput, tintNode.getInputFboConnection(1));
    }
}
