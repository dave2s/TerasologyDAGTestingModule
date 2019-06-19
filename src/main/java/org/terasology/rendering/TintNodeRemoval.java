/*
 * Copyright 2017 MovingBlocks
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
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.registry.In;
import org.terasology.rendering.dag.api.RenderDagApiInterface;
import org.terasology.rendering.dag.gsoc.NewNode;
import org.terasology.rendering.dag.nodes.TintNode;

// TODO NewAbstractNode to NewNode only!, no NewAbstractNode in here
// TODO Decide on what must by marked as @API or whitelisted (like nodes and such...use renderGraphAPI to access main DAG)
@RegisterSystem
public class TintNodeRemoval extends BaseComponentSystem {

    @In
    Context context;

    RenderDagApiInterface renderDagApi;

    @Override
    public void initialise() {
        super.initialise();
        renderDagApi = context.get(RenderDagApiInterface.class);

        moduleTintOutput();
    }

    private void moduleTintOutput() {
        // Create a new tintNode
        NewNode tintNode = new TintNode("tintNode", context);

        // Disconnect finalPostProcess and Output nodes
        // renderDagApi.disconnectOutputFbo("finalPostProcessingNode", 1);

        // Reconnect
        renderDagApi.reconnectInputFboToOutput(tintNode, 1, "finalPostProcessingNode", 1);
        renderDagApi.addNode(tintNode);

        renderDagApi.reconnectInputFboToOutput("outputToScreenNode", 1, "tintNode", 1);
    }
}
